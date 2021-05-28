package pl.sokols.watmerch.ui.account_section.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import dagger.hilt.android.AndroidEntryPoint
import pl.sokols.watmerch.BR
import pl.sokols.watmerch.R
import pl.sokols.watmerch.databinding.LoginFragmentBinding
import pl.sokols.watmerch.ui.MainActivity
import pl.sokols.watmerch.utils.Status
import pl.sokols.watmerch.utils.Utils

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private val viewModel: LoginViewModel by viewModels()
    private lateinit var binding: LoginFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = LoginFragmentBinding.inflate(inflater, container, false)
        binding.setVariable(BR.viewModel, viewModel)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).setActionBarTitle(getString(R.string.logging_page))
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setListeners()
    }

    private fun setListeners() {
        viewModel.isLoggedIn.observe(viewLifecycleOwner, { isLoggedIn ->
            if (isLoggedIn) {
                Navigation.findNavController(binding.root)
                    .navigate(R.id.action_loginFragment_to_accountFragment)
            }
        })

        binding.goToRegisterFromLoginTextView.setOnClickListener {
            Navigation.findNavController(binding.root)
                .navigate(R.id.action_loginFragment_to_registerFragment)
        }

        binding.loginButton.setOnClickListener {
            viewModel.onClickButton().observe(viewLifecycleOwner, {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> { }
                        Status.ERROR -> {
                            Utils.getSnackbar(
                                binding.root,
                                resource.message.toString(),
                                requireActivity()
                            ).show()
                        }
                        Status.LOADING -> { }
                    }
                }
            })
        }
    }
}
package pl.sokols.watmerch.ui.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import dagger.hilt.android.AndroidEntryPoint
import pl.sokols.watmerch.BR
import pl.sokols.watmerch.BasicApp
import pl.sokols.watmerch.R
import pl.sokols.watmerch.databinding.RegisterFragmentBinding
import pl.sokols.watmerch.utils.Status
import pl.sokols.watmerch.utils.Utils

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private val viewModel: RegisterViewModel by viewModels()
    private lateinit var binding: RegisterFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = RegisterFragmentBinding.inflate(inflater, container, false)
        binding.setVariable(BR.viewModel, viewModel)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setListeners()
    }

    private fun setListeners() {
        binding.goToLoginFromRegisterTextView.setOnClickListener { view ->
            view.findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }

        binding.registerButton.setOnClickListener {
            viewModel.onClickButton()?.observe(viewLifecycleOwner, {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            binding.registerProgressIndicator.visibility = View.INVISIBLE
                        }
                        Status.ERROR -> {
                            binding.registerProgressIndicator.visibility = View.INVISIBLE
                            Utils.getSnackbar(
                                binding.root,
                                resource.message.toString(),
                                requireActivity()
                            ).show()
                        }
                        Status.LOADING -> {
                            binding.registerProgressIndicator.visibility = View.VISIBLE
                        }
                    }
                }
            })
        }

        viewModel.isLoggedIn.observe(viewLifecycleOwner, { isLoggedIn ->
            if (isLoggedIn) {
                Navigation.findNavController(binding.root)
                    .navigate(R.id.action_registerFragment_to_accountFragment)
            }
        })

        viewModel.errorMessage.observe(viewLifecycleOwner, {
            if (it.isNotEmpty()) {
                Utils.getSnackbar(binding.root, it, requireActivity()).show()
            }
        })
    }
}
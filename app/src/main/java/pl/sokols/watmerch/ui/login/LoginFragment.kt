package pl.sokols.watmerch.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.google.android.material.snackbar.Snackbar
import pl.sokols.watmerch.BR
import pl.sokols.watmerch.BasicApp
import pl.sokols.watmerch.R
import pl.sokols.watmerch.databinding.LoginFragmentBinding
import pl.sokols.watmerch.utils.Status

class LoginFragment : Fragment() {

    companion object {
        fun newInstance() = LoginFragment()
    }

    private val viewModel: LoginViewModel by viewModels {
        LoginViewModelFactory((requireActivity().application as BasicApp).userRepository)
    }
    private lateinit var binding: LoginFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = LoginFragmentBinding.inflate(inflater, container, false)
        binding.setVariable(BR.viewModel, viewModel)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setListeners()
    }

    private fun setListeners() {
        binding.goToRegisterFromLoginTextView.setOnClickListener { view ->
            Navigation.findNavController(view)
                .navigate(R.id.action_loginFragment_to_registerFragment)
        }

        binding.loginButton.setOnClickListener {
            viewModel.onClickButton().observe(requireActivity(), {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            Snackbar.make(requireView(), resource.data.toString(), Snackbar.LENGTH_SHORT).show()
                        }
                        Status.ERROR -> {
                            Snackbar.make(requireView(), "ERROR", Snackbar.LENGTH_SHORT).show()
                        }
                        Status.LOADING -> {
                            Snackbar.make(requireView(), "LOADING", Snackbar.LENGTH_SHORT).show()
                        }
                    }
                }
            })

        }
    }
}
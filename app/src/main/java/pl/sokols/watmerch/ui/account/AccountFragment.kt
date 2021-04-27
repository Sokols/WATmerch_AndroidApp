package pl.sokols.watmerch.ui.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import pl.sokols.watmerch.BR
import pl.sokols.watmerch.BasicApp
import pl.sokols.watmerch.R
import pl.sokols.watmerch.databinding.AccountFragmentBinding

class AccountFragment : Fragment() {

    private val viewModel: AccountViewModel by viewModels {
        AccountViewModelFactory(requireActivity().application as BasicApp)
    }
    private lateinit var binding: AccountFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = AccountFragmentBinding.inflate(inflater, container, false)
        binding.setVariable(BR.viewModel, viewModel)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setListeners()
    }

    private fun setListeners() {
        binding.logoutAccountButton.setOnClickListener {
            viewModel.logout()
            Navigation.findNavController(binding.root)
                .navigate(R.id.action_accountFragment_to_loginFragment)
        }
    }
}
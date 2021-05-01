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
import pl.sokols.watmerch.data.model.User
import pl.sokols.watmerch.databinding.AccountFragmentBinding
import pl.sokols.watmerch.ui.account.adapters.SettingsAdapter
import pl.sokols.watmerch.utils.Status
import pl.sokols.watmerch.utils.Utils

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
        setRecyclerView()
    }

    private fun setListeners() {
        binding.logoutAccountButton.setOnClickListener {
            viewModel.logout()
            Navigation.findNavController(binding.root)
                .navigate(R.id.action_accountFragment_to_loginFragment)
        }
        viewModel.getUser().observe(viewLifecycleOwner, {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        binding.accountProgressIndicator.visibility = View.INVISIBLE
                        setUi(resource.data)
                    }
                    Status.ERROR -> {
                        binding.accountProgressIndicator.visibility = View.INVISIBLE
                    }
                    Status.LOADING -> {
                        binding.accountProgressIndicator.visibility = View.VISIBLE
                    }
                }
            }
        })
    }

    private fun setRecyclerView() {
        binding.settingsRecyclerView.adapter = SettingsAdapter(
            resources.getStringArray(R.array.settings).toList()
        )
    }

    private fun setUi(user: User?) {
        binding.setVariable(BR.user, user)
        binding.avatarAccountImageView.setImageBitmap(
            Utils.getBitmapFromString(
                user?.userDetails?.avatar
            )
        )
    }
}
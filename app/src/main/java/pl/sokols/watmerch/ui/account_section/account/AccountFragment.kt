package pl.sokols.watmerch.ui.account_section.account

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
import pl.sokols.watmerch.data.model.User
import pl.sokols.watmerch.databinding.AccountFragmentBinding
import pl.sokols.watmerch.ui.MainActivity
import pl.sokols.watmerch.ui.account_section.account.adapters.SettingsAdapter
import pl.sokols.watmerch.utils.Status
import pl.sokols.watmerch.utils.TransitionUtils
import pl.sokols.watmerch.utils.Utils

@AndroidEntryPoint
class AccountFragment : Fragment() {

    private val viewModel: AccountViewModel by viewModels()
    private lateinit var binding: AccountFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = AccountFragmentBinding.inflate(inflater, container, false)
        binding.setVariable(BR.viewModel, viewModel)
        setListeners()
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).setActionBarTitle(getString(R.string.account_page))
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
                    Status.SUCCESS -> { setUi(resource.data) }
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

    private fun setUi(user: User?) {
        binding.setVariable(BR.user, user)
        binding.avatarAccountImageView.setImageBitmap(Utils.getBitmapFromString(user?.userDetails?.avatar))
        binding.settingsRecyclerView.adapter = SettingsAdapter(
            resources.getStringArray(R.array.settings).toList(),
            user?.role?.name ?: getString(R.string.user_role)
        )
        TransitionUtils.fade(binding.accountLayout)
    }
}
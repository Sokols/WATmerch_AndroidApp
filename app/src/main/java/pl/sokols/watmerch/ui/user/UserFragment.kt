package pl.sokols.watmerch.ui.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import pl.sokols.watmerch.BasicApp
import pl.sokols.watmerch.data.model.User
import pl.sokols.watmerch.databinding.UserFragmentBinding
import pl.sokols.watmerch.utils.Status

@AndroidEntryPoint
class UserFragment : Fragment() {

    private val viewModel: UserViewModel by viewModels()
    private lateinit var binding: UserFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = UserFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setListeners()
    }

    private fun setListeners() {
        viewModel.getUser().observe(viewLifecycleOwner, {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        binding.userProgressIndicator.visibility = View.INVISIBLE
                        setUi(resource.data)
                    }
                    Status.ERROR -> {
                        binding.userProgressIndicator.visibility = View.INVISIBLE
                    }
                    Status.LOADING -> {
                        binding.userProgressIndicator.visibility = View.VISIBLE
                    }
                }
            }
        })
    }

    private fun setUi(user: User?) {
        binding.user = user
    }
}
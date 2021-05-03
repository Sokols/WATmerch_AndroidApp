package pl.sokols.watmerch.ui.user

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.AndroidEntryPoint
import pl.sokols.watmerch.R
import pl.sokols.watmerch.databinding.UserFragmentBinding
import pl.sokols.watmerch.utils.Status
import pl.sokols.watmerch.utils.Utils
import java.util.*


@AndroidEntryPoint
class UserFragment : Fragment() {

    private val viewModel: UserViewModel by viewModels()
    private lateinit var binding: UserFragmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        setEdit(menu.findItem(R.id.edit))
    }

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
                        binding.viewModel = viewModel
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

        binding.saveUserButton.setOnClickListener {
            viewModel.updateUserData().observe(viewLifecycleOwner, {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            binding.userProgressIndicator.visibility = View.INVISIBLE
                            findNavController().navigate(R.id.action_userFragment_to_accountFragment)
                        }
                        Status.ERROR -> {
                            binding.userProgressIndicator.visibility = View.INVISIBLE
                            Utils.getSnackbar(
                                binding.root,
                                resource.message.toString(),
                                requireActivity()
                            ).show()
                        }
                        Status.LOADING -> {
                            binding.userProgressIndicator.visibility = View.VISIBLE
                        }
                    }
                }
            })
        }

        binding.goBackUserTextView.setOnClickListener {
            findNavController().navigate(R.id.action_userFragment_to_accountFragment)
        }
    }

    private fun setEdit(item: MenuItem?) {
        if (item != null && arguments?.getString(Utils.PARENT_COMPONENT_ID) == getString(R.string.account_page)) {
            item.isVisible = true
            item.setOnMenuItemClickListener {
                viewModel.updateEditable()
                true
            }
            viewModel.updateEditable()
            binding.birthDateUserEditText.setOnClickListener(dateEditTextOnClickListener)
        }
    }

    private val dateEditTextOnClickListener = View.OnClickListener { v ->
        val datePicker = MaterialDatePicker.Builder
            .datePicker()
            .setTitleText(getString(R.string.select_date))
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .build()

        datePicker.addOnPositiveButtonClickListener {
            val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
            calendar.time = Date(it)
            binding.birthDateUserEditText.setText(Utils.getStringFromDate(calendar.time))
        }
        datePicker.show(parentFragmentManager, "TAG")
    }
}
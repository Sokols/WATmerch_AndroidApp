package pl.sokols.watmerch.ui.account_section.employee

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.zxing.integration.android.IntentIntegrator
import dagger.hilt.android.AndroidEntryPoint
import pl.sokols.watmerch.BR
import pl.sokols.watmerch.R
import pl.sokols.watmerch.databinding.EmployeeFragmentBinding
import pl.sokols.watmerch.ui.MainActivity
import pl.sokols.watmerch.ui.account_section.employee.adapters.EmployeeAdapter
import pl.sokols.watmerch.utils.Capture
import pl.sokols.watmerch.utils.Status

@AndroidEntryPoint
class EmployeeFragment : Fragment() {

    private val viewModel: EmployeeViewModel by viewModels()
    private lateinit var binding: EmployeeFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = EmployeeFragmentBinding.inflate(inflater, container, false)
        binding.setVariable(BR.viewModel, viewModel)
        setListeners()
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).setActionBarTitle(getString(R.string.employee_panel))
    }

    private fun setListeners() {
        binding.addProductEmployeeFAB.setOnClickListener {
            findNavController().navigate(R.id.action_employeeFragment_to_scannerFragment)
        }

        viewModel.getProducts().observe(viewLifecycleOwner, {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        binding.employeeRecyclerView.adapter = EmployeeAdapter(resource.data!!)
                    }
                    Status.ERROR -> {
                    }
                    Status.LOADING -> {
                    }
                }
            }
        })
    }
}
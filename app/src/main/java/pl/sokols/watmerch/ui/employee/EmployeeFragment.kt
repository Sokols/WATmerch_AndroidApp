package pl.sokols.watmerch.ui.employee

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import pl.sokols.watmerch.BR
import pl.sokols.watmerch.databinding.EmployeeFragmentBinding
import pl.sokols.watmerch.ui.employee.adapters.EmployeeAdapter
import pl.sokols.watmerch.ui.main.adapters.ProductListAdapter
import pl.sokols.watmerch.utils.Resource
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

    private fun setListeners() {
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
package pl.sokols.watmerch.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import pl.sokols.watmerch.BasicApp
import pl.sokols.watmerch.databinding.MainFragmentBinding
import pl.sokols.watmerch.ui.main.adapters.MerchListAdapter
import pl.sokols.watmerch.utils.Status
import pl.sokols.watmerch.utils.Utils

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory((requireActivity().application as BasicApp).userRepository)
    }
    private lateinit var binding: MainFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initComponents()
        initObservers()
    }

    private fun initObservers() {
        viewModel.getCategories().observe(requireActivity(), {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        Snackbar.make(requireView(), "SUCCESS", Snackbar.LENGTH_SHORT).show()
                        val merchListAdapter = MerchListAdapter(Utils.exampleArray())
                        binding.mainRecyclerView.adapter = merchListAdapter
                    }
                    Status.ERROR -> {
                        Snackbar.make(requireView(), resource.message.toString() + "\n" + resource.data.toString(), Snackbar.LENGTH_SHORT).show()
                    }
                    Status.LOADING -> {
                        Snackbar.make(requireView(), "LOADING", Snackbar.LENGTH_SHORT).show()
                    }
                }
            }
           })
    }

    private fun initComponents() {
        val merchListAdapter = MerchListAdapter(Utils.exampleArray())
        binding.mainRecyclerView.adapter = merchListAdapter
    }
}
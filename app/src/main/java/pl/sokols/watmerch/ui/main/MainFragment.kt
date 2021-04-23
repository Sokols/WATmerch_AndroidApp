package pl.sokols.watmerch.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import pl.sokols.watmerch.BasicApp
import pl.sokols.watmerch.databinding.MainFragmentBinding
import pl.sokols.watmerch.ui.main.adapters.ProductListAdapter
import pl.sokols.watmerch.utils.Status

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory(requireActivity().application as BasicApp)
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
        initObservers()
    }

    private fun initObservers() {
        viewModel.getProducts().observe(requireActivity(), {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        binding.mainProgressIndicator.visibility = INVISIBLE
                        val merchListAdapter =
                            ProductListAdapter(resource.data)
                        binding.mainRecyclerView.adapter = merchListAdapter
                    }
                    Status.ERROR -> {
                        binding.mainProgressIndicator.visibility = INVISIBLE
                    }
                    Status.LOADING -> {
                        binding.mainProgressIndicator.visibility = VISIBLE
                    }
                }
            }
        })
    }
}
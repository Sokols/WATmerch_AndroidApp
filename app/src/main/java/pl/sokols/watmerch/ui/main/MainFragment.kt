package pl.sokols.watmerch.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.chip.Chip
import pl.sokols.watmerch.BasicApp
import pl.sokols.watmerch.R
import pl.sokols.watmerch.data.model.Category
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
        viewModel.updated.observe(viewLifecycleOwner, {
            viewModel.getProducts().observe(viewLifecycleOwner, {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            binding.mainProgressIndicator.visibility = INVISIBLE
                            binding.mainRecyclerView.adapter = ProductListAdapter(resource.data)
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
        })

        viewModel.getCategories().observe(requireActivity(), {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        binding.mainProgressIndicator.visibility = INVISIBLE
                        setCategories(resource.data)
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

    private fun setCategories(data: List<Category>?) {
        if (data != null) {
            for (category: Category in data) {
                val mChip: Chip =
                    layoutInflater.inflate(R.layout.category_chip, null, false) as Chip
                mChip.text = category.name
                mChip.setOnCheckedChangeListener { _, isChecked ->
                    viewModel.updateCategory(category, isChecked)
                }
                binding.categoriesChipGroup.addView(mChip)
            }
        }
    }
}
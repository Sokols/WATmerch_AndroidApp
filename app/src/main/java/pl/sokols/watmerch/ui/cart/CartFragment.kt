package pl.sokols.watmerch.ui.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import pl.sokols.watmerch.BasicApp
import pl.sokols.watmerch.R
import pl.sokols.watmerch.data.model.Product
import pl.sokols.watmerch.databinding.CartFragmentBinding
import pl.sokols.watmerch.ui.cart.adapters.CartListAdapter
import pl.sokols.watmerch.utils.OnItemClickListener
import pl.sokols.watmerch.utils.Status
import pl.sokols.watmerch.utils.Utils

@AndroidEntryPoint
class CartFragment : Fragment() {

    companion object {
        fun newInstance() = CartFragment()
    }

    private val viewModel: CartViewModel by viewModels()
    private lateinit var binding: CartFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = CartFragmentBinding.inflate(inflater, container, false)
        initComponents()
        return binding.root
    }

    private fun initComponents() {
        viewModel.getSharedPreferencesLiveData().observe(viewLifecycleOwner, {
            viewModel.updateProducts().observe(viewLifecycleOwner, {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            binding.cartProgressIndicator.visibility = View.INVISIBLE
                            binding.cartRecyclerView.adapter =
                                CartListAdapter(resource.data!!, deleteListener)
                        }
                        Status.ERROR -> {
                            binding.cartProgressIndicator.visibility = View.INVISIBLE
                        }
                        Status.LOADING -> {
                            binding.cartProgressIndicator.visibility = View.VISIBLE
                        }
                    }
                }
            })
        })
    }

    private val deleteListener = object : OnItemClickListener {
        override fun onClick(item: Any) {
            viewModel.delete(item as Product)
            Utils.getSnackbar(
                binding.root,
                getString(R.string.removed_from_cart),
                requireActivity()
            ).setAction(R.string.cancel) {
                viewModel.insert(item as Product)
            }.show()
        }
    }
}
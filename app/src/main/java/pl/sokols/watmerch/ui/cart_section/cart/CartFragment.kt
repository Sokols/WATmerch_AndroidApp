package pl.sokols.watmerch.ui.cart_section.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import pl.sokols.watmerch.R
import pl.sokols.watmerch.data.model.OrderProduct
import pl.sokols.watmerch.databinding.CartFragmentBinding
import pl.sokols.watmerch.ui.MainActivity
import pl.sokols.watmerch.ui.cart_section.cart.adapters.CartListAdapter
import pl.sokols.watmerch.utils.callbacks.OnItemClickCallback
import pl.sokols.watmerch.utils.Utils

@AndroidEntryPoint
class CartFragment : Fragment() {

    companion object {
        fun newInstance() = CartFragment()
    }

    private val viewModel: CartViewModel by viewModels()
    private lateinit var binding: CartFragmentBinding
    private lateinit var recyclerViewAdapter: CartListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = CartFragmentBinding.inflate(inflater, container, false)

        setComponents()
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).setActionBarTitle(getString(R.string.cart_page))
    }

    private fun setComponents() {
        binding.viewModel = viewModel
        recyclerViewAdapter = CartListAdapter(deleteListener, incrementListener, decrementListener)
        binding.cartRecyclerView.adapter = recyclerViewAdapter
        viewModel.products.observe(viewLifecycleOwner, { orderProducts ->
            if (orderProducts.isEmpty()) {
                binding.cartRecyclerView.visibility = View.GONE
                binding.cartEmptyTextView.visibility = View.VISIBLE
            } else {
                recyclerViewAdapter.submitList(Utils.cloneList(orderProducts))
                binding.cartEmptyTextView.visibility = View.GONE
                binding.cartRecyclerView.visibility = View.VISIBLE
            }
            viewModel.setTotal(orderProducts)
        })

        binding.payCartButton.setOnClickListener {
            if (viewModel.products.value?.isEmpty() == true) {
                Utils.getSnackbar(
                    binding.root,
                    getString(R.string.empty_cart),
                    requireActivity()
                ).show()
            } else {
                findNavController().navigate(R.id.action_cartFragment_to_addressFragment)
            }
        }
    }

    private val deleteListener = object : OnItemClickCallback {
        override fun onClick(item: Any) {
            viewModel.deleteProduct((item as OrderProduct).product!!.barcode)
            Utils.getSnackbar(
                binding.root,
                getString(R.string.removed_from_cart),
                requireActivity()
            ).setAction(R.string.cancel) {
                viewModel.insertProduct(item.product!!.barcode)
            }.show()
        }
    }

    private val incrementListener = object : OnItemClickCallback {
        override fun onClick(item: Any) {
            viewModel.updateTotal((item as OrderProduct), true)
        }
    }

    private val decrementListener = object : OnItemClickCallback {
        override fun onClick(item: Any) {
            viewModel.updateTotal((item as OrderProduct), false)
        }
    }
}
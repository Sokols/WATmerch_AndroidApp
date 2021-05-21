package pl.sokols.watmerch.ui.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
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
        setComponents()
        return binding.root
    }

    private fun setComponents() {
        binding.viewModel = viewModel
        viewModel.products.observe(viewLifecycleOwner, { orderProducts ->
            viewModel.updateProducts(orderProducts!!).observe(viewLifecycleOwner, {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            binding.cartRecyclerView.adapter =
                                CartListAdapter(
                                    orderProducts,
                                    resource.data!!,
                                    deleteListener,
                                    incrementListener,
                                    decrementListener
                                )
                            binding.cartProgressIndicator.visibility = View.INVISIBLE
                        }
                        Status.ERROR -> {
                            binding.cartProgressIndicator.visibility = View.INVISIBLE
                            Utils.getSnackbar(
                                binding.root,
                                resource.message.toString(),
                                requireActivity()
                            ).show()
                        }
                        Status.LOADING -> {
                            binding.cartProgressIndicator.visibility = View.VISIBLE
                        }
                    }
                }
            })
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

    private val deleteListener = object : OnItemClickListener {
        override fun onClick(item: Any) {
            viewModel.deleteProduct((item as Product).barcode)
            Utils.getSnackbar(
                binding.root,
                getString(R.string.removed_from_cart),
                requireActivity()
            ).setAction(R.string.cancel) {
                viewModel.insertProduct(item.barcode)
            }.show()
        }
    }

    private val incrementListener = object : OnItemClickListener {
        override fun onClick(item: Any) {
            viewModel.updateTotal((item as Product), true)
        }
    }

    private val decrementListener = object : OnItemClickListener {
        override fun onClick(item: Any) {
            viewModel.updateTotal((item as Product), false)
        }
    }
}
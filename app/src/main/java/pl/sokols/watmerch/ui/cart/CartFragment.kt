package pl.sokols.watmerch.ui.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import pl.sokols.watmerch.R
import pl.sokols.watmerch.data.model.Product
import pl.sokols.watmerch.databinding.CartFragmentBinding
import pl.sokols.watmerch.ui.cart.adapters.CartListAdapter
import pl.sokols.watmerch.utils.OnItemClickListener
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
        viewModel.products.observe(viewLifecycleOwner, { orderProducts ->
            viewModel.updateProducts(orderProducts!!).observe(viewLifecycleOwner, { products ->
                binding.cartRecyclerView.adapter =
                    CartListAdapter(
                        products,
                        deleteListener,
                        incrementListener,
                        decrementListener
                    )
                binding.viewModel = viewModel
            })
        })
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
            viewModel.updateTotal((item as Product).price)
        }
    }

    private val decrementListener = object : OnItemClickListener {
        override fun onClick(item: Any) {
            viewModel.updateTotal((item as Product).price * -1)
        }
    }
}
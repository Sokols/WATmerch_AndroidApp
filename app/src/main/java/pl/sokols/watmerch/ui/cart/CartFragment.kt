package pl.sokols.watmerch.ui.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import pl.sokols.watmerch.BasicApp
import pl.sokols.watmerch.R
import pl.sokols.watmerch.data.model.Product
import pl.sokols.watmerch.databinding.CartFragmentBinding
import pl.sokols.watmerch.ui.cart.adapters.CartListAdapter
import pl.sokols.watmerch.ui.cart.adapters.OnItemClickListener

class CartFragment : Fragment() {

    companion object {
        fun newInstance() = CartFragment()
    }

    private val viewModel: CartViewModel by viewModels {
        CartViewModelFactory((requireActivity().application as BasicApp).merchRepository)
    }
    private lateinit var binding: CartFragmentBinding

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.deleteAll) {
            viewModel.deleteAll()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = CartFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initComponents()
    }

    private fun initComponents() {
        viewModel.allProduct.observe(viewLifecycleOwner, { merch ->
            binding.cartRecyclerView.adapter = CartListAdapter(merch, deleteListener)
        })
    }

    private val deleteListener = object : OnItemClickListener {
        override fun onClick(product: Product) {
            viewModel.delete(product)
            val snackbar = Snackbar.make(
                binding.root,
                getString(R.string.removed_from_cart),
                Snackbar.LENGTH_SHORT
            ).setAction(R.string.cancel) {
                viewModel.insert(product)
            }
            snackbar.anchorView = requireActivity().findViewById(R.id.bottom_navigation)
            snackbar.show()
        }
    }
}
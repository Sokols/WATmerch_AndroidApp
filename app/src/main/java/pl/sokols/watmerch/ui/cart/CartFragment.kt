package pl.sokols.watmerch.ui.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import pl.sokols.watmerch.BasicApp
import pl.sokols.watmerch.R
import pl.sokols.watmerch.data.model.Product
import pl.sokols.watmerch.databinding.CartFragmentBinding
import pl.sokols.watmerch.ui.cart.adapters.CartListAdapter
import pl.sokols.watmerch.ui.cart.adapters.OnItemClickListener
import pl.sokols.watmerch.utils.Utils

class CartFragment : Fragment() {

    companion object {
        fun newInstance() = CartFragment()
    }

    private val viewModel: CartViewModel by viewModels {
        CartViewModelFactory(requireActivity().application as BasicApp)
    }
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
            lifecycleScope.launch { viewModel.updateProducts() }
        })
        viewModel.products.observe(viewLifecycleOwner, {
            binding.cartRecyclerView.adapter =
                CartListAdapter(it, deleteListener)
        })
    }

    private val deleteListener = object : OnItemClickListener {
        override fun onClick(product: Product) {
            viewModel.delete(product)
            Utils.getSnackbar(
                binding.root,
                getString(R.string.removed_from_cart),
                requireActivity()
            ).setAction(R.string.cancel) {
                viewModel.insert(product)
            }.show()
        }
    }
}
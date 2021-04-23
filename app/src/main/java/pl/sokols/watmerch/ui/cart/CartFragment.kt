package pl.sokols.watmerch.ui.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.deleteAll) {

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
    }

//    private val deleteListener = object : OnItemClickListener {
//        override fun onClick(product: Product) {
//            viewModel.delete(product)
//
//            Utils.getSnackbar(
//                binding.root,
//                getString(R.string.removed_from_cart),
//                requireActivity()
//            ).setAction(R.string.cancel) {
//                viewModel.insert(product)
//            }.show()
//        }
//    }
}
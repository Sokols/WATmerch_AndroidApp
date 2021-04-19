package pl.sokols.watmerch.ui.cart

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import pl.sokols.watmerch.BasicApp
import pl.sokols.watmerch.R
import pl.sokols.watmerch.data.model.Merch
import pl.sokols.watmerch.databinding.CartFragmentBinding
import pl.sokols.watmerch.ui.cart.adapters.CartListAdapter
import pl.sokols.watmerch.ui.cart.adapters.OnItemClickListener

class CartFragment : Fragment() {

    companion object {
        fun newInstance() = CartFragment()
    }

    private val viewModel: CartViewModel by viewModels {
        CartViewModelFactory((requireActivity().application as BasicApp).repository)
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
        viewModel.allMerch.observe(viewLifecycleOwner, { merch ->
            binding.cartRecyclerView.adapter = CartListAdapter(merch, deleteListener)
        })
    }

    private val deleteListener = object : OnItemClickListener {
        override fun onClick(merch: Merch) {
            viewModel.delete(merch)
        }
    }
}
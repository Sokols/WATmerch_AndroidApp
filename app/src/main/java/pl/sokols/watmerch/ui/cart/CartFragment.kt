package pl.sokols.watmerch.ui.cart

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import pl.sokols.watmerch.BasicApp
import pl.sokols.watmerch.databinding.CartFragmentBinding
import pl.sokols.watmerch.ui.cart.adapters.CartListAdapter

class CartFragment : Fragment() {

    companion object {
        fun newInstance() = CartFragment()
    }

    private val viewModel: CartViewModel by viewModels {
        CartViewModelFactory((requireActivity().application as BasicApp).repository)
    }
    private lateinit var binding: CartFragmentBinding

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
            binding.cartRecyclerView.adapter = CartListAdapter(merch)
        })
    }
}
package pl.sokols.watmerch.ui.product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import pl.sokols.watmerch.BasicApp
import pl.sokols.watmerch.R
import pl.sokols.watmerch.utils.Utils
import pl.sokols.watmerch.data.model.Product
import pl.sokols.watmerch.databinding.MerchFragmentBinding

class ProductFragment : Fragment() {

    companion object {
        fun newInstance() = ProductFragment()
    }

    private val viewModel: MerchViewModel by viewModels {
        MerchViewModelFactory((requireActivity().application as BasicApp).merchRepository)
    }
    private lateinit var binding: MerchFragmentBinding
    private lateinit var product: Product

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MerchFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        product = arguments?.getParcelable(Utils.MERCH_ITEM_KEY)!!
        setBinding()
        setObservers()
    }

    private fun setObservers() {
        binding.addToCartMerchButton.setOnClickListener {
            viewModel.insert(product)
            findNavController().navigate(R.id.action_merchFragment_to_mainFragment)
            val snackbar = Snackbar.make(
                binding.root,
                getString(R.string.added_to_cart),
                Snackbar.LENGTH_SHORT
            )
            snackbar.anchorView = requireActivity().findViewById(R.id.bottom_navigation)
            snackbar.show()
        }
    }

    private fun setBinding() {
        binding.titleMerchTextView.text = product.name
        binding.priceMerchTextView.text =
            String.format(context?.getString(R.string.price).toString(), product.price)
    }
}
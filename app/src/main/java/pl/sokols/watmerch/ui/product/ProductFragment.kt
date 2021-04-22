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
import pl.sokols.watmerch.data.model.Product
import pl.sokols.watmerch.databinding.ProductFragmentBinding
import pl.sokols.watmerch.utils.Utils

class ProductFragment : Fragment() {

    companion object {
        fun newInstance() = ProductFragment()
    }

    private val viewModel: MerchViewModel by viewModels {
        MerchViewModelFactory((requireActivity().application as BasicApp).merchRepository)
    }
    private lateinit var binding: ProductFragmentBinding
    private lateinit var product: Product

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ProductFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        product = arguments?.getParcelable(Utils.PRODUCT_ITEM_KEY)!!
        setBinding()
        setObservers()
    }

    private fun setObservers() {
        binding.addToCartProductButton.setOnClickListener {
            viewModel.insert(product)
            findNavController().navigate(R.id.action_productFragment_to_mainFragment)
            Utils.getSnackbar(binding.root, getString(R.string.added_to_cart), requireActivity())
                .show()
        }
    }

    private fun setBinding() {
        binding.titleProductTextView.text = product.name
        binding.priceProductTextView.text =
            String.format(context?.getString(R.string.price).toString(), product.price)
    }
}
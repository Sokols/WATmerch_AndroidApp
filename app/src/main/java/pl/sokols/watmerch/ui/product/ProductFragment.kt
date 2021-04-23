package pl.sokols.watmerch.ui.product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import pl.sokols.watmerch.BR
import pl.sokols.watmerch.BasicApp
import pl.sokols.watmerch.R
import pl.sokols.watmerch.databinding.ProductFragmentBinding
import pl.sokols.watmerch.utils.Utils

class ProductFragment : Fragment() {

    companion object {
        fun newInstance() = ProductFragment()
    }

    private val viewModel: ProductViewModel by viewModels {
        ProductViewModelFactory(requireActivity().application as BasicApp)
    }
    private lateinit var binding: ProductFragmentBinding
    private var barcode: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ProductFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        barcode = arguments?.getInt(Utils.PRODUCT_BARCODE)!!
        setObservers()
    }

    private fun setObservers() {
        viewModel.getProductByBarcode(barcode)
            .observe(viewLifecycleOwner, {
                binding.setVariable(BR.product, it.data)
            })
        binding.addToCartProductButton.setOnClickListener {
            if (!viewModel.isProductInCartAlready(barcode)) {
                findNavController().navigate(R.id.action_productFragment_to_mainFragment)
                Utils.getSnackbar(
                    binding.root,
                    getString(R.string.added_to_cart),
                    requireActivity()
                ).show()
            } else {
                Utils.getSnackbar(
                    binding.root,
                    getString(R.string.already_in_cart),
                    requireActivity()
                ).show()
            }
        }
    }
}
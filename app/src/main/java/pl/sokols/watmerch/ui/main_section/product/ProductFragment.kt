package pl.sokols.watmerch.ui.main_section.product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import pl.sokols.watmerch.BR
import pl.sokols.watmerch.R
import pl.sokols.watmerch.databinding.ProductFragmentBinding
import pl.sokols.watmerch.ui.MainActivity
import pl.sokols.watmerch.utils.Status
import pl.sokols.watmerch.utils.Utils

@AndroidEntryPoint
class ProductFragment : Fragment() {

    private val viewModel: ProductViewModel by viewModels()
    private lateinit var binding: ProductFragmentBinding
    private var barcode: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ProductFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).setActionBarTitle(getString(R.string.blank))
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        barcode = arguments?.getInt(Utils.PRODUCT_BARCODE)!!
        setObservers()
    }

    private fun setObservers() {
        viewModel.getProductByBarcode(barcode).observe(requireActivity(), {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        binding.setVariable(BR.product, it.data)
                        binding.productImageView.setImageBitmap(Utils.getBitmapFromString(it.data?.basicDetails?.logoImage))
                    }
                    Status.ERROR -> { }
                    Status.LOADING -> { }
                }
            }
        })

        viewModel.isProductInCartAlready(barcode).observe(viewLifecycleOwner, {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        if (resource.data == true) {
                            disableAddToCartButton()
                        }
                    }
                    Status.ERROR -> {
                        Utils.getSnackbar(
                            binding.root,
                            resource.message.toString(),
                            requireActivity()
                        ).show()
                    }
                    Status.LOADING -> { }
                }
            }
        })

        binding.addToCartProductButton.setOnClickListener {
            viewModel.addProductToCart(barcode).observe(viewLifecycleOwner, {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            Utils.getSnackbar(
                                binding.root,
                                getString(R.string.added_to_cart),
                                requireActivity()
                            ).show()
                            findNavController().navigate(R.id.action_productFragment_to_mainFragment)
                        }
                        Status.ERROR -> {
                            Utils.getSnackbar(
                                binding.root,
                                resource.message.toString(),
                                requireActivity()
                            ).show()
                        }
                        Status.LOADING -> { }
                    }
                }
            })
        }
    }

    private fun disableAddToCartButton() {
        binding.addToCartProductButton.text = getString(R.string.already_in_cart)
        binding.addToCartProductButton.isClickable = false
        binding.addToCartProductButton.isFocusable = false
    }
}
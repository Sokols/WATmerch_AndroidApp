package pl.sokols.watmerch.ui.account_section.scanner

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.journeyapps.barcodescanner.BarcodeCallback
import dagger.hilt.android.AndroidEntryPoint
import pl.sokols.watmerch.R
import pl.sokols.watmerch.data.model.Product
import pl.sokols.watmerch.databinding.ScannerFragmentBinding
import pl.sokols.watmerch.utils.Status
import pl.sokols.watmerch.utils.Utils


@AndroidEntryPoint
class ScannerFragment : Fragment() {

    private val viewModel: ScannerViewModel by viewModels()
    private lateinit var binding: ScannerFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ScannerFragmentBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        setBarcode()
        setListeners()
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        binding.barcodeScanner.resume()
    }

    override fun onPause() {
        super.onPause()
        binding.barcodeScanner.pause()
    }

    private fun setListeners() {
        binding.applyScannerButton.setOnClickListener {
            if (checkIfAllDataProvided()) {
                viewModel.addEditProduct()
                    .observe(viewLifecycleOwner, {
                        it.let { resource ->
                            when (resource.status) {
                                Status.SUCCESS -> {
                                    Utils.getSnackbar(
                                        binding.root,
                                        getString(R.string.done),
                                        requireActivity()
                                    ).show()
                                    clearAll()
                                }
                                Status.ERROR -> {
                                }
                                Status.LOADING -> {
                                }
                            }
                        }
                    })
            } else {
                Utils.getSnackbar(
                    binding.root,
                    getString(R.string.provide_all_data),
                    requireActivity()
                ).show()
            }
        }
    }

    private fun setBarcode() {
        binding.barcodeScanner.initializeFromIntent(activity?.intent)
        binding.barcodeScanner.decodeContinuous(callback)
    }

    private val callback: BarcodeCallback = BarcodeCallback { result ->
        viewModel.getProductByBarcode(result.text.toInt()).observe(viewLifecycleOwner, {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        viewModel.product.set(resource.data)
                    }
                    Status.ERROR -> {
                        Utils.getSnackbar(
                            binding.root,
                            getString(R.string.add_new_product),
                            requireActivity()
                        ).show()
                        viewModel.product.set(Product(result.text.toInt(), "", 0.0f))
                    }
                    Status.LOADING -> {
                    }
                }
            }
        })
    }
//
//    private fun getUpdatedProduct(product: Product?): Product? {
//        product?.barcode = binding.productBarcodeScannerEditText.text.toString().toInt()
//        product?.name = binding.productNameScannerEditText.text.toString()
//        product?.price = binding.productPriceScannerEditText.text.toString().toFloat()
//        return product
//    }

    private fun checkIfAllDataProvided(): Boolean {
        return binding.productBarcodeScannerEditText.text.toString().isNotEmpty()
                && binding.productNameScannerEditText.text.toString().isNotEmpty()
                && binding.productPriceScannerEditText.text.toString().isNotEmpty()
    }


    private fun clearAll() {
        viewModel.product.set(Product(0, "", 0.0f))
    }
}

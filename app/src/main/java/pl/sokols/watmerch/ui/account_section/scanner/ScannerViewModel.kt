package pl.sokols.watmerch.ui.account_section.scanner

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import pl.sokols.watmerch.data.model.Product
import pl.sokols.watmerch.data.model.ProductBasicDetails
import pl.sokols.watmerch.data.model.ProductDetails
import pl.sokols.watmerch.data.repository.CategoryRepository
import pl.sokols.watmerch.data.repository.ProductRepository
import pl.sokols.watmerch.utils.Resource
import javax.inject.Inject

@HiltViewModel
class ScannerViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val categoryRepository: CategoryRepository
) : ViewModel() {

    var product: ObservableField<Product> = ObservableField(Product(0, "", 0.0f))

    fun getProductByBarcode(barcode: Int) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = productRepository.getProductByBarcode(barcode)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
            Log.d("ERROR", exception.message.toString())
        }
    }

    fun addEditProduct() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = productRepository.addEditProduct(prepareProductToAdd())))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
            Log.d("ERROR", exception.message.toString())
        }
    }


    // TODO: Mocking!
    private suspend fun prepareProductToAdd(): Product {
        val product = product.get()!!
        if (product.basicDetails == null) {
            product.basicDetails = ProductBasicDetails(shortDescription = "test")
        }

        if (product.category == null) {
            product.category = categoryRepository.getCategories()[0]
        }

        if (product.details == null) {
            product.details = ProductDetails(quantityInStock = 1)
        }
        return product
    }

}
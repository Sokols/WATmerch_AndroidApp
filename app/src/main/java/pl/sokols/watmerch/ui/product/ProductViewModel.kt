package pl.sokols.watmerch.ui.product

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import pl.sokols.watmerch.data.model.OrderProduct
import pl.sokols.watmerch.data.repository.OrderProductRepository
import pl.sokols.watmerch.data.repository.ProductRepository
import pl.sokols.watmerch.utils.AppPreferences
import pl.sokols.watmerch.utils.Resource
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val orderProductRepository: OrderProductRepository
) : ViewModel() {

    fun getProductByBarcode(barcode: Int) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = productRepository.getProductByBarcode(barcode)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
            Log.d("ERROR", exception.message.toString())
        }
    }

    fun isProductInCartAlready(barcode: Int) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = orderProductRepository.isInTheCart(barcode)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
            Log.d("ERROR", exception.message.toString())
        }
    }

    fun addProductToCart(barcode: Int) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            val orderProduct = OrderProduct(productBarcode = barcode)
            emit(Resource.success(data = orderProductRepository.insertOrderProduct(orderProduct)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
            Log.d("ERROR", exception.message.toString())
        }
    }
}
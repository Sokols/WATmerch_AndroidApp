package pl.sokols.watmerch.ui.product

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers
import pl.sokols.watmerch.BasicApp
import pl.sokols.watmerch.data.remote.services.ProductService
import pl.sokols.watmerch.data.repository.ProductRepository
import pl.sokols.watmerch.utils.AppPreferences
import pl.sokols.watmerch.utils.Resource

class ProductViewModel(private val repository: ProductRepository) : ViewModel() {

    fun getProductByBarcode(barcode: Int) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = repository.getProductByBarcode(barcode)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
            Log.d("ERROR", exception.message.toString())
        }
    }

    fun isProductInCartAlready(barcode: Int): Boolean {
        val products = AppPreferences.cartProductsBarcodes
        if (!products!!.contains(barcode)) {
            products.add(barcode)
            AppPreferences.cartProductsBarcodes = products
            return false
        }
        return true
    }
}

class ProductViewModelFactory(private val basicApp: BasicApp) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            val productService = basicApp.retrofit.createService(ProductService::class.java)
            return ProductViewModel(ProductRepository(productService)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
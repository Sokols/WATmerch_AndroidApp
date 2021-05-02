package pl.sokols.watmerch.ui.product

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import pl.sokols.watmerch.BasicApp
import pl.sokols.watmerch.data.remote.services.product.ProductService
import pl.sokols.watmerch.data.repository.ProductRepository
import pl.sokols.watmerch.utils.AppPreferences
import pl.sokols.watmerch.utils.Resource
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val repository: ProductRepository,
    private val prefs: AppPreferences
) : ViewModel() {

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
        val products = prefs.cartProductsBarcodes
        if (!products!!.contains(barcode)) {
            products.add(barcode)
            prefs.cartProductsBarcodes = products
            return false
        }
        return true
    }
}
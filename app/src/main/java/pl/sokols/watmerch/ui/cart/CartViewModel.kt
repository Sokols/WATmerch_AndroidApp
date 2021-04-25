package pl.sokols.watmerch.ui.cart

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import pl.sokols.watmerch.BasicApp
import pl.sokols.watmerch.data.model.Product
import pl.sokols.watmerch.data.remote.services.ProductService
import pl.sokols.watmerch.data.repository.ProductRepository
import pl.sokols.watmerch.utils.AppPreferences
import pl.sokols.watmerch.utils.Resource
import pl.sokols.watmerch.utils.SharedPreferenceLiveData

class CartViewModel(
    private val repository: ProductRepository,
    private val sharedPreferenceLiveData: SharedPreferenceLiveData
) : ViewModel() {

    fun getSharedPreferencesLiveData() = sharedPreferenceLiveData

    fun updateProducts() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = coroutineScope {
                val productsHashSet = AppPreferences.cartProductsBarcodes
                val productList: MutableList<Product> = arrayListOf()
                for (barcode: Int in productsHashSet!!) {
                    productList.add(repository.getProductByBarcode(barcode))
                }
                productList.toList()
            }))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
            Log.d("ERROR", exception.message.toString())
        }
    }

    fun delete(product: Product) {
        val barcodes = AppPreferences.cartProductsBarcodes
        barcodes!!.remove(product.barcode)
        AppPreferences.cartProductsBarcodes = barcodes
    }

    fun insert(product: Product) {
        val barcodes = AppPreferences.cartProductsBarcodes
        barcodes!!.add(product.barcode)
        AppPreferences.cartProductsBarcodes = barcodes
    }
}

class CartViewModelFactory(private val basicApp: BasicApp) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CartViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            val productService = basicApp.retrofit.createService(ProductService::class.java)
            val sharedPreferenceLiveData =
                SharedPreferenceLiveData(AppPreferences.sharedPreferences!!)
            return CartViewModel(ProductRepository(productService), sharedPreferenceLiveData) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
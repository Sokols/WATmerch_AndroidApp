package pl.sokols.watmerch.ui.cart

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import pl.sokols.watmerch.data.model.Product
import pl.sokols.watmerch.data.repository.ProductRepository
import pl.sokols.watmerch.utils.AppPreferences
import pl.sokols.watmerch.utils.Resource
import pl.sokols.watmerch.utils.SharedPrefsCartProductsLiveData
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val repository: ProductRepository,
    private val sharedPrefsCartProductsLiveData: SharedPrefsCartProductsLiveData,
    private val prefs: AppPreferences
) : ViewModel() {

    var total: ObservableField<Float> = ObservableField(0f)

    fun getSharedPreferencesLiveData() = sharedPrefsCartProductsLiveData

    fun updateProducts() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = coroutineScope {
                val productsHashSet = prefs.cartProductsBarcodes
                val productList: MutableList<Product> = arrayListOf()
                for (barcode: Int in productsHashSet!!) {
                    productList.add(repository.getProductByBarcode(barcode))
                }
                setTotal(productList)
                productList.toList()
            }))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
            Log.d("ERROR", exception.message.toString())
        }
    }

    fun delete(product: Product) {
        val barcodes = prefs.cartProductsBarcodes
        barcodes!!.remove(product.barcode)
        prefs.cartProductsBarcodes = barcodes
    }

    fun insert(product: Product) {
        val barcodes = prefs.cartProductsBarcodes
        barcodes!!.add(product.barcode)
        prefs.cartProductsBarcodes = barcodes
    }

    fun updateTotal(price: Float) {
        total.set(total.get()?.plus(price))
    }

    private fun setTotal(products: List<Product>) {
        var sum = 0f
        for (product: Product in products) {
            sum += product.price
        }
        total.set(sum)
    }
}
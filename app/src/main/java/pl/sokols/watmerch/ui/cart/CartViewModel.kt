package pl.sokols.watmerch.ui.cart

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import pl.sokols.watmerch.BasicApp
import pl.sokols.watmerch.data.model.Product
import pl.sokols.watmerch.data.remote.services.ProductService
import pl.sokols.watmerch.data.repository.ProductRepository
import pl.sokols.watmerch.utils.AppPreferences
import pl.sokols.watmerch.utils.SharedPreferenceLiveData

class CartViewModel(
    private val repository: ProductRepository,
    private val sharedPreferenceLiveData: SharedPreferenceLiveData
) : ViewModel() {

    fun getSharedPreferencesLiveData() = sharedPreferenceLiveData
    val products: MutableLiveData<List<Product>> = MutableLiveData()

    suspend fun updateProducts() {
        val productsHashSet = AppPreferences.cartProductsBarcodes
        val productList: MutableList<Product> = arrayListOf()
        for (barcode: Int in productsHashSet!!) {
            productList.add(repository.getProductByBarcode(barcode))
        }
        products.value = productList.toList()
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
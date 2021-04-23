package pl.sokols.watmerch.ui.cart

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import pl.sokols.watmerch.BasicApp
import pl.sokols.watmerch.data.model.Product
import pl.sokols.watmerch.data.remote.services.ProductService
import pl.sokols.watmerch.data.repository.ProductRepository

class CartViewModel(private val repository: ProductRepository) : ViewModel() {

}

class CartViewModelFactory(private val basicApp: BasicApp) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CartViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            val productService = basicApp.retrofit.createService(ProductService::class.java)
            return CartViewModel(ProductRepository(productService)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

//val allProduct: LiveData<List<Product>> = repository.allProduct.asLiveData()
//
//fun deleteAll() = viewModelScope.launch {
//    repository.deleteAllFromDb()
//}
//
//fun delete(product:Product) = viewModelScope.launch {
//    repository.deleteFromDb(product)
//}
//
//fun insert(product: Product) = viewModelScope.launch {
//    repository.insertToDb(product)
//}
package pl.sokols.watmerch.ui.cart

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import pl.sokols.watmerch.data.model.Product
import pl.sokols.watmerch.data.repository.CartProductsRepository

class CartViewModel(private val repository: CartProductsRepository) : ViewModel() {
    val allProduct: LiveData<List<Product>> = repository.allProduct.asLiveData()

    fun deleteAll() = viewModelScope.launch {
        repository.deleteAll()
    }

    fun delete(product:Product) = viewModelScope.launch {
        repository.delete(product)
    }

    fun insert(product: Product) = viewModelScope.launch {
        repository.insert(product)
    }
}

class CartViewModelFactory(private val repository: CartProductsRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CartViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CartViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
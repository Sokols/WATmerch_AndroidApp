package pl.sokols.watmerch.ui.product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pl.sokols.watmerch.data.model.Product
import pl.sokols.watmerch.data.repository.CartProductsRepository

class MerchViewModel(private val repository: CartProductsRepository) : ViewModel() {

    fun insert(product: Product) = viewModelScope.launch {
        repository.insert(product)
    }
}

class MerchViewModelFactory(private val repository: CartProductsRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MerchViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MerchViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
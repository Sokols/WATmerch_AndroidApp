package pl.sokols.watmerch.ui.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import pl.sokols.watmerch.data.model.Merch
import pl.sokols.watmerch.data.repository.MerchRepository

class CartViewModel(private val repository: MerchRepository) : ViewModel() {
    val allMerch: LiveData<List<Merch>> = repository.allMerch.asLiveData()
}

class CartViewModelFactory(private val repository: MerchRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CartViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CartViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
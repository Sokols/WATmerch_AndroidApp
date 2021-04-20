package pl.sokols.watmerch.ui.merch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pl.sokols.watmerch.data.model.Merch
import pl.sokols.watmerch.data.repository.MerchRepository

class MerchViewModel(private val repository: MerchRepository) : ViewModel() {

    fun insert(merch: Merch) = viewModelScope.launch {
        repository.insert(merch)
    }
}

class MerchViewModelFactory(private val repository: MerchRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MerchViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MerchViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
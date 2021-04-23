package pl.sokols.watmerch.ui.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers
import pl.sokols.watmerch.BasicApp
import pl.sokols.watmerch.data.remote.services.ProductService
import pl.sokols.watmerch.data.repository.ProductRepository
import pl.sokols.watmerch.utils.Resource

class MainViewModel(private val repository: ProductRepository) : ViewModel() {

    fun getProducts() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = repository.getProducts().content))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
            Log.d("ERROR", exception.message.toString())
        }
    }
}

class MainViewModelFactory(private val basicApp: BasicApp) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            val productService = basicApp.retrofit.createService(ProductService::class.java)
            return MainViewModel(ProductRepository(productService)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
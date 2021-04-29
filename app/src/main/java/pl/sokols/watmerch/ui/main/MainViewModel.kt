package pl.sokols.watmerch.ui.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers
import pl.sokols.watmerch.BasicApp
import pl.sokols.watmerch.data.model.Category
import pl.sokols.watmerch.data.remote.services.CategoryService
import pl.sokols.watmerch.data.remote.services.ProductService
import pl.sokols.watmerch.data.repository.CategoryRepository
import pl.sokols.watmerch.data.repository.ProductRepository
import pl.sokols.watmerch.utils.Resource
import kotlin.collections.set

class MainViewModel(
    private val productRepository: ProductRepository,
    private val categoryRepository: CategoryRepository
) : ViewModel() {

    private var options: MutableMap<String, String> = mutableMapOf("extended" to "true")
    var updated: MutableLiveData<Boolean> = MutableLiveData(true)

    fun getProducts() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = productRepository.getProducts(options.toMap()).content))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
            Log.d("ERROR", exception.message.toString())
        }
    }

    fun getCategories() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = categoryRepository.getCategories()))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
            Log.d("ERROR", exception.message.toString())
        }
    }

    fun updateCategory(category: Category, isAdding: Boolean) {
        if (isAdding) {
            options["category"] = category.id.toString()
        } else {
            options.remove("category")
        }
        updated.value = true
    }

}

class MainViewModelFactory(private val basicApp: BasicApp) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            val productService = basicApp.retrofit.createService(ProductService::class.java)
            val categoryService = basicApp.retrofit.createService(CategoryService::class.java)
            return MainViewModel(
                ProductRepository(productService),
                CategoryRepository(categoryService)
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
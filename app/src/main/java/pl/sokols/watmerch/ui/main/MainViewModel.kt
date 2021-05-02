package pl.sokols.watmerch.ui.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import pl.sokols.watmerch.BasicApp
import pl.sokols.watmerch.data.model.Category
import pl.sokols.watmerch.data.remote.services.category.CategoryService
import pl.sokols.watmerch.data.remote.services.product.ProductService
import pl.sokols.watmerch.data.repository.CategoryRepository
import pl.sokols.watmerch.data.repository.ProductRepository
import pl.sokols.watmerch.utils.Resource
import javax.inject.Inject
import kotlin.collections.set

@HiltViewModel
class MainViewModel @Inject constructor(
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

    fun updateCategory(category: Category, isChecked: Boolean) {
        if (isChecked) {
            if (category.id != 0) {
                options["category"] = category.id.toString()
            } else {
                options.remove("category")
            }
            updated.value = true
        }
    }

    fun updateSearch(query: String) {
        if (query.isNotEmpty()) {
            options["contains"] = query
        } else {
            options.remove("contains")
        }
        updated.value = true
    }
}
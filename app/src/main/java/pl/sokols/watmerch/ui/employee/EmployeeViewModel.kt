package pl.sokols.watmerch.ui.employee

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import pl.sokols.watmerch.data.repository.ProductRepository
import pl.sokols.watmerch.utils.Resource
import javax.inject.Inject

@HiltViewModel
class EmployeeViewModel @Inject constructor(
    private val repository: ProductRepository
): ViewModel() {

    private var options: MutableMap<String, String> = mutableMapOf()

    fun getProducts() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = repository.getProducts(options.toMap()).content))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
            Log.d("ERROR", exception.message.toString())
        }
    }
}
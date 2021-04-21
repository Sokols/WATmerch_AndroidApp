package pl.sokols.watmerch.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers
import pl.sokols.watmerch.data.model.LoginRequest
import pl.sokols.watmerch.data.model.LoginResponse
import pl.sokols.watmerch.data.repository.RemoteRepository
import pl.sokols.watmerch.utils.Resource

class LoginViewModel(private val repository: RemoteRepository) : ViewModel() {

    var username: String = ""
    var password: String = ""

    private fun loginUser() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = repository.loginUser(LoginRequest(username, password))))
            repository.updateClient(username, password)
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun onClickButton(): LiveData<Resource<LoginResponse>> {
        return loginUser()
    }
}

class LoginViewModelFactory(private val repository: RemoteRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LoginViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
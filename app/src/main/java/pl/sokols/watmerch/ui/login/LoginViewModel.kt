package pl.sokols.watmerch.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers
import pl.sokols.watmerch.BasicApp
import pl.sokols.watmerch.data.model.request.LoginRequest
import pl.sokols.watmerch.data.model.response.LoginResponse
import pl.sokols.watmerch.data.remote.services.UserService
import pl.sokols.watmerch.data.repository.ProductRepository
import pl.sokols.watmerch.data.repository.UserRepository
import pl.sokols.watmerch.utils.Resource

class LoginViewModel(private val repository: UserRepository) : ViewModel() {

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

class LoginViewModelFactory(private val basicApp: BasicApp) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            val userService = basicApp.retrofit.createService(UserService::class.java)
            return LoginViewModel(UserRepository(basicApp.retrofit, userService)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
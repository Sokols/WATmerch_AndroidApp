package pl.sokols.watmerch.ui.login

import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import pl.sokols.watmerch.BasicApp
import pl.sokols.watmerch.data.model.request.LoginRequest
import pl.sokols.watmerch.data.model.response.UserResponse
import pl.sokols.watmerch.data.remote.services.UserService
import pl.sokols.watmerch.data.repository.UserRepository
import pl.sokols.watmerch.utils.AppPreferences
import pl.sokols.watmerch.utils.Resource

class LoginViewModel(private val repository: UserRepository) : ViewModel() {

    val isLoggedIn: MutableLiveData<Boolean> by lazy { MutableLiveData(AppPreferences.authToken != null) }
    var username: String = ""
    var password: String = ""

    private fun loginUser() = liveData(Dispatchers.Main) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = repository.loginUser(LoginRequest(username, password))))
            repository.loginRetrofit(username, password)
            isLoggedIn.value = true
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
            Log.d("ERROR", exception.message.toString())
        }
    }

    fun onClickButton(): LiveData<Resource<UserResponse>> {
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
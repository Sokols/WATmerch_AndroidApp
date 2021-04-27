package pl.sokols.watmerch.ui.register

import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import pl.sokols.watmerch.BasicApp
import pl.sokols.watmerch.data.model.User
import pl.sokols.watmerch.data.remote.services.UserService
import pl.sokols.watmerch.data.repository.UserRepository
import pl.sokols.watmerch.utils.AppPreferences
import pl.sokols.watmerch.utils.Resource

class RegisterViewModel(private val repository: UserRepository) : ViewModel() {

    val isLoggedIn: MutableLiveData<Boolean> by lazy { MutableLiveData(AppPreferences.authToken != null) }
    val errorMessage: MutableLiveData<String> = MutableLiveData()
    var username: String = ""
    var email: String = ""
    var password: String = ""
    var password2: String = ""

    private fun createUser() = liveData(Dispatchers.Main) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = repository.createUser(User(username=username, email=email, password=password))))
            repository.loginRetrofit(username, password)
            isLoggedIn.value = true
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
            Log.d("ERROR", exception.message.toString())
        }
    }

    fun onClickButton(): LiveData<Resource<User>>? {
        return when {
            username.isEmpty() || email.isEmpty() || password.isEmpty() || password2.isEmpty() -> {
                errorMessage.value = "Uzupełnij wszystkie pola!"
                return null
            }
            password != password2 -> {
                errorMessage.value = "Hasła nie są identyczne!"
                return null
            }
            else -> {
                errorMessage.value = ""
                createUser()
            }
        }
    }
}

class RegisterViewModelFactory(private val basicApp: BasicApp) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            val userService = basicApp.retrofit.createService(UserService::class.java)
            return RegisterViewModel(UserRepository(basicApp.retrofit, userService)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

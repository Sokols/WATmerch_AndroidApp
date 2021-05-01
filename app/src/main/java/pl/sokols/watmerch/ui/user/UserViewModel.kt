package pl.sokols.watmerch.ui.user

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers
import pl.sokols.watmerch.BasicApp
import pl.sokols.watmerch.data.model.User
import pl.sokols.watmerch.data.remote.services.UserService
import pl.sokols.watmerch.data.repository.UserRepository
import pl.sokols.watmerch.utils.AppPreferences
import pl.sokols.watmerch.utils.Resource

class UserViewModel(private val repository: UserRepository) : ViewModel() {

    fun getUser() = liveData(Dispatchers.Main) {
        emit(Resource.loading(data = null))
        try {
            emit(
                Resource.success(
                    data = repository.loginUser(
                        User(
                            username = AppPreferences.userUsername.toString(),
                            password = AppPreferences.userPassword.toString()
                        )
                    )
                )
            )
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
            Log.d("ERROR", exception.message.toString())
        }
    }
}


class UserViewModelFactory(private val basicApp: BasicApp) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            val userService = basicApp.retrofit.createService(UserService::class.java)
            return UserViewModel(UserRepository(basicApp.retrofit, userService)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
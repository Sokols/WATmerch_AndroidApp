package pl.sokols.watmerch.ui.account_section.login

import android.util.Log
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import pl.sokols.watmerch.data.model.User
import pl.sokols.watmerch.data.repository.UserRepository
import pl.sokols.watmerch.utils.AppPreferences
import pl.sokols.watmerch.utils.Resource
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: UserRepository,
    private val prefs: AppPreferences
) : ViewModel() {

    val isLoggedIn: MutableLiveData<Boolean> by lazy { MutableLiveData(prefs.authToken != null) }
    var username: String = ""
    var password: String = ""

    private fun loginUser() = liveData(Dispatchers.Main) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = repository.loginUser(User(username, password))))
            repository.loginRetrofit(username, password)
            isLoggedIn.value = true
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
            Log.d("ERROR", exception.message.toString())
        }
    }

    fun onClickButton(): LiveData<Resource<User?>> {
        return loginUser()
    }
}
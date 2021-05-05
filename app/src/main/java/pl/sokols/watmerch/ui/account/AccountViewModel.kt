package pl.sokols.watmerch.ui.account

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import pl.sokols.watmerch.data.model.User
import pl.sokols.watmerch.data.repository.UserRepository
import pl.sokols.watmerch.utils.AppPreferences
import pl.sokols.watmerch.utils.Resource
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val repository: UserRepository,
    private val prefs: AppPreferences
) : ViewModel() {

    fun getUser() = liveData(Dispatchers.Main) {
        emit(Resource.loading(data = null))
        try {
            emit(
                Resource.success(
                    data = repository.loginUser(
                        User(
                            username = prefs.userUsername.toString(),
                            password = prefs.userPassword.toString()
                        )
                    )
                )
            )
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
            Log.d("ERROR", exception.message.toString())
        }
    }

    fun logout() {
        repository.logoutRetrofit()
    }
}
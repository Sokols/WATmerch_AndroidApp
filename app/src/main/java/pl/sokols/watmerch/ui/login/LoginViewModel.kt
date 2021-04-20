package pl.sokols.watmerch.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pl.sokols.watmerch.data.model.LoginRequest
import pl.sokols.watmerch.data.repository.UserRepository

class LoginViewModel(private val repository: UserRepository) : ViewModel() {

    var username: String = ""
    var password: String = ""

    private fun loginUser() = viewModelScope.launch {
//        repository.loginUser(LoginRequest(username, password))
          repository.loginUser(LoginRequest("user", "user"))

    }

    fun onClickButton() {
        loginUser()
    }
}

class LoginViewModelFactory(private val repository: UserRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LoginViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
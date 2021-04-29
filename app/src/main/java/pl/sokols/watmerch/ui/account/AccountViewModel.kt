package pl.sokols.watmerch.ui.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import pl.sokols.watmerch.BasicApp
import pl.sokols.watmerch.data.remote.services.UserService
import pl.sokols.watmerch.data.repository.UserRepository

class AccountViewModel(private val repository: UserRepository) : ViewModel() {

    fun logout() {
        repository.logoutRetrofit()
    }
}

class AccountViewModelFactory(private val basicApp: BasicApp) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AccountViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            val userService = basicApp.retrofit.createService(UserService::class.java)
            return AccountViewModel(UserRepository(basicApp.retrofit, userService)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
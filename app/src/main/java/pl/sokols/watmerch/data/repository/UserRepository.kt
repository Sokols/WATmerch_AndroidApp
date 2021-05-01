package pl.sokols.watmerch.data.repository

import pl.sokols.watmerch.data.model.User
import pl.sokols.watmerch.data.remote.ServiceBuilder
import pl.sokols.watmerch.data.remote.services.UserService
import pl.sokols.watmerch.utils.AppPreferences

class UserRepository(
    private val retrofit: ServiceBuilder,
    private val userService: UserService
) {
    suspend fun loginUser(user: User) = userService.loginUser(user)

    suspend fun createUser(user: User) = userService.createUser(user)

    fun loginRetrofit(username: String, password: String) {
        AppPreferences.userUsername = username
        AppPreferences.userPassword = password
        retrofit.createService(UserService::class.java, username, password)
    }

    fun logoutRetrofit() {
        AppPreferences.userUsername = null
        AppPreferences.userPassword = null
        AppPreferences.authToken = null
        AppPreferences.cookies = null
        retrofit.updateRetrofit()
    }
}

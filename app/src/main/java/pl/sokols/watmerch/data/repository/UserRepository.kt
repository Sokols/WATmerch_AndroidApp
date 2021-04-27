package pl.sokols.watmerch.data.repository

import pl.sokols.watmerch.data.model.User
import pl.sokols.watmerch.data.model.request.LoginRequest
import pl.sokols.watmerch.data.remote.ServiceBuilder
import pl.sokols.watmerch.data.remote.services.UserService
import pl.sokols.watmerch.utils.AppPreferences

class UserRepository(
    private val retrofit: ServiceBuilder,
    private val userService: UserService
) {
    suspend fun loginUser(loginRequest: LoginRequest) = userService.loginUser(loginRequest)

    suspend fun createUser(user: User) = userService.createUser(user)

    // Helpers
    fun loginRetrofit(username: String, password: String) =
        retrofit.createService(UserService::class.java, username, password)

    fun logoutRetrofit() {
        AppPreferences.authToken = null
        AppPreferences.cookies = null
        retrofit.updateRetrofit()
    }
}

package pl.sokols.watmerch.data.repository

import pl.sokols.watmerch.data.model.User
import pl.sokols.watmerch.data.remote.services.user.UserHelper
import pl.sokols.watmerch.utils.AppPreferences
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userHelper: UserHelper,
    private val prefs: AppPreferences
) {
    suspend fun loginUser(user: User) = userHelper.loginUser(user)

    suspend fun createUser(user: User) = userHelper.createUser(user)

    fun loginRetrofit(username: String, password: String) {
        prefs.userUsername = username
        prefs.userPassword = password
//        retrofit.createService(UserService::class.java, username, password)
    }

    fun logoutRetrofit() {
        prefs.userUsername = null
        prefs.userPassword = null
        prefs.authToken = null
        prefs.cookies = null
//        retrofit.updateRetrofit()
    }
}

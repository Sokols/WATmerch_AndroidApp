package pl.sokols.watmerch.data.repository

import pl.sokols.watmerch.data.model.User
import pl.sokols.watmerch.data.model.UserDetails
import pl.sokols.watmerch.data.remote.services.user.UserHelper
import pl.sokols.watmerch.di.PreferencesModule
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val userHelper: UserHelper,
    private val prefs: PreferencesModule
) {
    suspend fun loginUser(user: User): User? = userHelper.loginUser(user)

    suspend fun createUser(user: User) = userHelper.createUser(user)

    suspend fun editUserDetails(userDetails: UserDetails) = userHelper.editUserDetails(userDetails)

    fun loginRetrofit(username: String, password: String) {
        prefs.userUsername = username
        prefs.userPassword = password
    }

    fun logoutRetrofit() {
        prefs.userUsername = null
        prefs.userPassword = null
        prefs.authToken = null
        prefs.cookies = null
    }
}

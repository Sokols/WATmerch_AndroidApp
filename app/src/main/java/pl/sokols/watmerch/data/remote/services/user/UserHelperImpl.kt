package pl.sokols.watmerch.data.remote.services.user

import pl.sokols.watmerch.data.model.User
import javax.inject.Inject

class UserHelperImpl @Inject constructor(private val userService: UserService) : UserHelper {
    override suspend fun loginUser(user: User): User = userService.loginUser(user)

    override suspend fun createUser(user: User): User = userService.createUser(user)
}
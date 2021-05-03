package pl.sokols.watmerch.data.remote.services.user

import pl.sokols.watmerch.data.model.User
import pl.sokols.watmerch.data.model.UserDetails
import javax.inject.Inject

class UserHelperImpl @Inject constructor(private val userService: UserService) : UserHelper {
    override suspend fun loginUser(user: User): User = userService.loginUser(user)

    override suspend fun createUser(user: User): User = userService.createUser(user)

    override suspend fun editUserDetails(userDetails: UserDetails): UserDetails = userService.editUserDetails(userDetails)
}
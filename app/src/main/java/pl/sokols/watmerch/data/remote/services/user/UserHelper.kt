package pl.sokols.watmerch.data.remote.services.user

import pl.sokols.watmerch.data.model.User
import pl.sokols.watmerch.data.model.UserDetails

interface UserHelper {

    suspend fun loginUser(user: User): User?

    suspend fun createUser(user: User): User

    suspend fun editUserDetails(userDetails: UserDetails): UserDetails
}
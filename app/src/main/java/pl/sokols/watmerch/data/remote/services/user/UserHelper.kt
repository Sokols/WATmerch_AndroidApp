package pl.sokols.watmerch.data.remote.services.user

import pl.sokols.watmerch.data.model.User

interface UserHelper {

    suspend fun loginUser(user: User): User

    suspend fun createUser(user: User): User
}
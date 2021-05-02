package pl.sokols.watmerch.data.remote.services.user

import pl.sokols.watmerch.data.model.User
import retrofit2.http.Body
import retrofit2.http.POST

interface UserService {
    @POST("api/loginUser")
    suspend fun loginUser(@Body user: User): User

    @POST("api/register")
    suspend fun createUser(@Body user: User): User
}
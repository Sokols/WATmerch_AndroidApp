package pl.sokols.watmerch.data.remote.services

import pl.sokols.watmerch.data.model.*
import pl.sokols.watmerch.data.model.request.LoginRequest
import pl.sokols.watmerch.data.model.response.UserResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface UserService {
    @POST("api/loginUser")
    suspend fun loginUser(@Body loginRequest: LoginRequest): UserResponse

    @POST("api/register")
    suspend fun createUser(@Body user: User): User
}
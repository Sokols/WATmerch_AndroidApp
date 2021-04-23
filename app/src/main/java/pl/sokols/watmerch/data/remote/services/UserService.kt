package pl.sokols.watmerch.data.remote.services

import pl.sokols.watmerch.data.model.*
import pl.sokols.watmerch.data.model.request.LoginRequest
import pl.sokols.watmerch.data.model.response.LoginResponse
import pl.sokols.watmerch.data.model.response.ProductResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface UserService {
    @POST("api/loginUser")
    suspend fun loginUser(@Body loginRequest: LoginRequest): LoginResponse

    @POST("api/register")
    suspend fun createUser(@Body user: User): User
}
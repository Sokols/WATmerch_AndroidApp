package pl.sokols.watmerch.data.remote

import pl.sokols.watmerch.data.model.LoginRequest
import pl.sokols.watmerch.data.model.LoginResponse
import pl.sokols.watmerch.data.model.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {

    @Headers("Content-Type: application/json")
    @POST("api/loginUser")
    suspend fun loginUser(@Body loginRequest: LoginRequest): Call<LoginResponse>

    @POST("api/register")
    suspend fun createUser(@Body user: User): Call<User>
}
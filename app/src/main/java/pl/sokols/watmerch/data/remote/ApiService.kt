package pl.sokols.watmerch.data.remote

import pl.sokols.watmerch.data.model.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {

    @GET("api/categories")
    suspend fun getCategories(): Category

    @GET("api/products")
    suspend fun getProducts(): Product

    @POST("api/loginUser")
    suspend fun loginUser(@Body loginRequest: LoginRequest): LoginResponse

    @POST("api/register")
    suspend fun createUser(@Body user: User): User
}
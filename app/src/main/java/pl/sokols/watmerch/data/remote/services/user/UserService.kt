package pl.sokols.watmerch.data.remote.services.user

import pl.sokols.watmerch.data.model.User
import pl.sokols.watmerch.data.model.UserDetails
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT

interface UserService {
    @POST("api/loginUser")
    suspend fun loginUser(@Body user: User): User

    @POST("api/register")
    suspend fun createUser(@Body user: User): User

    @PUT("api/editUserDetails")
    suspend fun editUserDetails(@Body userDetails: UserDetails): UserDetails
}
package pl.sokols.watmerch.data.repository

import android.util.Log
import pl.sokols.watmerch.data.model.LoginRequest
import pl.sokols.watmerch.data.model.LoginResponse
import pl.sokols.watmerch.data.remote.ApiService
import pl.sokols.watmerch.data.remote.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository {

    var client = ServiceBuilder.buildService(ApiService::class.java)

    suspend fun loginUser(loginRequest: LoginRequest) = client.loginUser(loginRequest).enqueue(
        object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    Log.d("SUCCESS", response.message())
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.d("ERROR", t.message.toString())
            }

        }
    )
}
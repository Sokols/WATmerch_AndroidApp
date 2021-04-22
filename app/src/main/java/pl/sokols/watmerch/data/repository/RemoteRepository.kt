package pl.sokols.watmerch.data.repository

import android.util.Log
import pl.sokols.watmerch.data.model.LoginRequest
import pl.sokols.watmerch.data.remote.ApiService
import pl.sokols.watmerch.data.remote.ServiceBuilder

class RemoteRepository {

    companion object {
        @Volatile
        private var INSTANCE: RemoteRepository? = null

        fun getRemoteRepository() : RemoteRepository {
            return INSTANCE ?: synchronized(this) {
                val instance = RemoteRepository()
                INSTANCE = instance
                instance
            }
        }
    }

    // TODO: only for tests - remove!
    private var client = ServiceBuilder.createService(ApiService::class.java, "admin", "admin")
//    private var client = ServiceBuilder.createService(ApiService::class.java)

    suspend fun loginUser(loginRequest: LoginRequest) = client.loginUser(loginRequest)

    suspend fun getCategories() = client.getCategories()

    suspend fun getProducts() = client.getProducts()


    // Helpers
    fun updateClient(username: String, password: String) {
        client = ServiceBuilder.createService(ApiService::class.java, username, password)
    }
}

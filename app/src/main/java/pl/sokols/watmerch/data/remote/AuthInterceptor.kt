package pl.sokols.watmerch.data.remote

import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class AuthInterceptor(private val credentials: String?) : Interceptor {
    constructor(username: String, password: String) :
            this(
                credentials = Credentials.basic(username, password)
            )

    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        val authenticatedRequest: Request = request.newBuilder()
            .header("Authorization", credentials)
            .build()
        return chain.proceed(authenticatedRequest)
    }
}
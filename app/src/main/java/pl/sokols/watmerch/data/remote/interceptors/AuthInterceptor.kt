package pl.sokols.watmerch.data.remote.interceptors

import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import pl.sokols.watmerch.di.PreferencesModule
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthInterceptor @Inject constructor(
    private val prefs: PreferencesModule
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        val username = prefs.userUsername
        val password = prefs.userPassword

        if (username != null && password != null) {
            val credentials = Credentials.basic(username, password)
            prefs.authToken = credentials
            val authenticatedRequest: Request = request.newBuilder()
                .header("Authorization", credentials)
                .build()
            return chain.proceed(authenticatedRequest)
        }

        return chain.proceed(request)
    }
}
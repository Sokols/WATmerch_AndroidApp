package pl.sokols.watmerch.data.remote

import android.text.TextUtils
import okhttp3.Credentials
import okhttp3.OkHttpClient
import pl.sokols.watmerch.data.remote.interceptors.AddCookiesInterceptor
import pl.sokols.watmerch.data.remote.interceptors.AuthInterceptor
import pl.sokols.watmerch.data.remote.interceptors.ReceivedCookiesInterceptor
import pl.sokols.watmerch.utils.AppPreferences
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ServiceBuilder {

    private val URL = "http://10.0.2.2:8081/"

    private val httpClient: OkHttpClient.Builder = let {
        val builder = OkHttpClient.Builder()
        val authToken: String? = AppPreferences.authToken
        if (authToken != null) {
            builder.addInterceptor(AuthInterceptor(authToken))
        }
        builder.addInterceptor(AddCookiesInterceptor())
            .addInterceptor(ReceivedCookiesInterceptor())
    }

    private val builder: Retrofit.Builder = Retrofit.Builder()
        .baseUrl(URL)
        .addConverterFactory(GsonConverterFactory.create())

    private var retrofit: Retrofit = builder.build()

    fun <S> createService(serviceClass: Class<S>): S {
        return createService(serviceClass, null, null)
    }

    fun <S> createService(
        serviceClass: Class<S>, username: String?, password: String?
    ): S {
        if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)) {
            val authToken: String = Credentials.basic(username, password)
            return createService(serviceClass, authToken)
        }
        return createService(serviceClass, null)
    }

    private fun <S> createService(
        serviceClass: Class<S>, authToken: String?
    ): S {
        if (!TextUtils.isEmpty(authToken)) {
            val interceptor = AuthInterceptor(authToken)
            if (!httpClient.interceptors().contains(interceptor)) {
                httpClient.addInterceptor(interceptor)

                builder.client(httpClient.build())
                retrofit = builder.build()
            }
        }

        return retrofit.create(serviceClass)
    }

}
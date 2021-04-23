package pl.sokols.watmerch.data.remote.interceptors

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
import pl.sokols.watmerch.BasicApp
import pl.sokols.watmerch.utils.AppPreferences
import pl.sokols.watmerch.utils.Utils

class ReceivedCookiesInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val response: Response = chain.proceed(chain.request())

        if (response.headers("Set-Cookie").isNotEmpty()) {
            val cookies: HashSet<String> = HashSet()
            for (header: String in response.headers("Set-Cookie")) {
                Log.i("INFO", "Intercept cookie is: $header")
                cookies.add(header)
            }

            AppPreferences.cookies = cookies
        }

        return response
    }
}
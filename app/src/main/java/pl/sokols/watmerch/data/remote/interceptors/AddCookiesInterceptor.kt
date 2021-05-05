package pl.sokols.watmerch.data.remote.interceptors

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import pl.sokols.watmerch.utils.AppPreferences
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AddCookiesInterceptor @Inject constructor(
    private val prefs: AppPreferences
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val builder: Request.Builder = chain.request().newBuilder()
        val preferences: HashSet<String>? = prefs.cookies

        if (preferences != null) {
            for (cookie: String in preferences) {
                Log.i("INFO", "Added cookie is: $cookie")
                builder.addHeader("Cookie", cookie)
            }
        }
        return chain.proceed(builder.build())
    }
}
package pl.sokols.watmerch

import android.app.Application
import android.content.Context
import pl.sokols.watmerch.data.remote.ServiceBuilder
import pl.sokols.watmerch.utils.AppPreferences

class BasicApp : Application() {
    val retrofit by lazy { ServiceBuilder() }

    override fun onCreate() {
        super.onCreate()
        AppPreferences.setup(applicationContext)
    }
}
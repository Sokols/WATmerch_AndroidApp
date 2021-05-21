package pl.sokols.watmerch

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp
import pl.sokols.watmerch.utils.LocaleHelper

@HiltAndroidApp
class BasicApp : Application() {
    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(LocaleHelper.onAttach(base!!, "pl"))
    }
}
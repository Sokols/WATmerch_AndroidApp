package pl.sokols.watmerch.utils

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import pl.sokols.watmerch.R
import java.util.*

class LocaleHelper {

    companion object {

        fun onAttach(context: Context): Context? =
            setLocale(context, getPersistedData(context, Locale.getDefault().language))

        fun onAttach(context: Context, defaultLanguage: String): Context? =
            setLocale(context, getPersistedData(context, defaultLanguage))

        fun setLocale(context: Context, language: String): Context? =
            updateResources(context, language)

        private fun getPersistedData(context: Context, defaultLanguage: String): String =
            when (AppPreferences(context).language) {
                context.getString(R.string.polish) -> "pl"
                context.getString(R.string.english) -> "en"
                else -> defaultLanguage
            }

        @TargetApi(Build.VERSION_CODES.N)
        private fun updateResources(context: Context, language: String): Context? {
            val locale = Locale(language)
            Locale.setDefault(locale)
            val configuration = context.resources.configuration
            configuration.setLocale(locale)
            return context.createConfigurationContext(configuration)
        }
    }
}

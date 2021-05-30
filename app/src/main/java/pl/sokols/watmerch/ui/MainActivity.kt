package pl.sokols.watmerch.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import pl.sokols.watmerch.R
import pl.sokols.watmerch.data.remote.websockets.WebSocketListener
import pl.sokols.watmerch.data.remote.websockets.WebSocketMessageModel
import pl.sokols.watmerch.data.remote.websockets.WebSocketModule
import pl.sokols.watmerch.databinding.MainActivityBinding
import pl.sokols.watmerch.di.PreferencesModule
import pl.sokols.watmerch.utils.LocaleHelper
import pl.sokols.watmerch.utils.NotificationUtils


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: MainActivityBinding
    private lateinit var prefs: PreferencesModule

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.topAppBar)
        setAppPreferences()
        setNavigation()
        setWebSocket()
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase!!))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.top_app_bar, menu)
        return true
    }

    fun setActionBarTitle(title: String?) {
        supportActionBar!!.title = title
    }

    private fun setAppPreferences() {
        prefs = PreferencesModule(this)
        if (prefs.theme == null) {
            prefs.theme = getString(R.string.light_theme)
        }
        if (prefs.language == null) {
            prefs.language = getString(R.string.polish)
        }

        prefs.prefs.registerOnSharedPreferenceChangeListener(listener)
    }

    private fun setNavigation() {
        // set bottom navigation
        binding.bottomNavigation.setupWithNavController(
            Navigation.findNavController(
                this,
                R.id.nav_host_fragment
            )
        )
    }

    private fun setWebSocket() {
        WebSocketModule().provideWebSocket(object : WebSocketModule(), WebSocketListener {
            override fun onSocketListener(webSocketMessageModel: WebSocketMessageModel) {
                if (webSocketMessageModel.name == "WATmerch") {
                    NotificationUtils.displayNotification(
                        this@MainActivity,
                        webSocketMessageModel.name.toString(),
                        webSocketMessageModel.message.toString()
                    )
                }
            }
        })
    }

    private fun setLanguage() {
        if (prefs.language.equals(getString(R.string.polish))) {
            LocaleHelper.setLocale(this, "pl")
        } else {
            LocaleHelper.setLocale(this, "en")
        }
    }

    private fun setTheme() {
        if (prefs.theme == getString(R.string.light_theme)) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
    }

    private val listener: SharedPreferences.OnSharedPreferenceChangeListener =
        SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
            when {
                key.equals(PreferencesModule.Key.THEME.toString()) -> {
                    setTheme()
                }
                key.equals(PreferencesModule.Key.LANGUAGE.toString()) -> {
                    setLanguage()
                    recreate()
                }
            }
        }
}
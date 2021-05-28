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
import pl.sokols.watmerch.databinding.MainActivityBinding
import pl.sokols.watmerch.utils.AppPreferences
import pl.sokols.watmerch.utils.LocaleHelper


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: MainActivityBinding
    private lateinit var prefs: AppPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        setAppPreferences()
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.topAppBar)
        setNavigation()
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase!!))
    }

    private fun setAppPreferences() {
        prefs = AppPreferences(this)
        if (prefs.theme == null) {
            prefs.theme = getString(R.string.light_theme)
        }
        if (prefs.language == null) {
            prefs.language = getString(R.string.polish)
        }

        prefs.prefs.registerOnSharedPreferenceChangeListener(listener)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.top_app_bar, menu)
        return true
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

    fun setActionBarTitle(title: String?) {
        supportActionBar!!.title = title
    }

    private val listener: SharedPreferences.OnSharedPreferenceChangeListener =
        SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
            when {
                key.equals(AppPreferences.Key.THEME.toString()) -> {
                    setTheme()
                }
                key.equals(AppPreferences.Key.LANGUAGE.toString()) -> {
                    setLanguage()
                    recreate()
                }
            }
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
}
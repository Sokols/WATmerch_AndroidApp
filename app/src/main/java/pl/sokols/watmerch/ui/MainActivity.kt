package pl.sokols.watmerch.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.Navigation
import androidx.navigation.findNavController
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
        setNavigationAndMenu()
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

    private fun setNavigationAndMenu() {
        // set bottom navigation
        binding.bottomNavigation.setupWithNavController(
            Navigation.findNavController(
                this,
                R.id.nav_host_fragment
            )
        )

        // set search menu item for main fragment
        // set menu title
        findNavController(R.id.nav_host_fragment).addOnDestinationChangedListener { _: NavController, navDestination: NavDestination, _: Bundle? ->
            title = when (navDestination.id) {
                R.id.mainFragment -> getString(R.string.main_page)
                R.id.cartFragment -> getString(R.string.cart_page)
                R.id.productFragment -> getString(R.string.product_description)
                R.id.accountFragment -> getString(R.string.account_page)
                R.id.loginFragment -> getString(R.string.logging_page)
                R.id.registerFragment -> getString(R.string.registing_page)
                R.id.userFragment -> getString(R.string.user_page)
                else -> getString(R.string.blank)
            }
        }
    }

    private val listener: SharedPreferences.OnSharedPreferenceChangeListener =
        SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
            when {
                key.equals(AppPreferences.Key.THEME.toString()) -> {
                    if (prefs.theme.equals(getString(R.string.light_theme))) {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    } else {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    }
                }
                key.equals(AppPreferences.Key.LANGUAGE.toString()) -> {
                    if (prefs.language.equals(getString(R.string.polish))) {
                        LocaleHelper.setLocale(this, "pl")
                    } else {
                        LocaleHelper.setLocale(this, "en")
                    }
                    recreate()
                }
            }
        }
}
package pl.sokols.watmerch.ui

import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import pl.sokols.watmerch.R
import pl.sokols.watmerch.databinding.MainActivityBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setNavigationAndMenu()
    }

    override fun onBackPressed() {
        AlertDialog.Builder(this)
            .setMessage(getString(R.string.are_you_sure_you_want_to_quit))
            .setCancelable(false)
            .setPositiveButton(
                getString(R.string.yes)
            ) { _, _ -> super@MainActivity.onBackPressed() }
            .setNegativeButton(getString(R.string.no), null)
            .show()
    }

    private fun setNavigationAndMenu() {
        binding.bottomNavigation.setupWithNavController(
            Navigation.findNavController(
                this,
                R.id.nav_host_fragment
            )
        )

        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.changeTheme -> {
                    when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
                        Configuration.UI_MODE_NIGHT_YES ->
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                        Configuration.UI_MODE_NIGHT_NO ->
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    }
                    true
                }
                R.id.search -> {
                    // Handle search icon press
                    true
                }
                R.id.more -> {
                    // Handle more item (inside overflow menu) press
                    true
                }
                else -> false
            }
        }
    }
}
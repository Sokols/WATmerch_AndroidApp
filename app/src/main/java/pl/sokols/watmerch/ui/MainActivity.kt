package pl.sokols.watmerch.ui

import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.Navigation
import androidx.navigation.findNavController
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
            binding.topAppBar.title = when (navDestination.id) {
                R.id.mainFragment -> getString(R.string.main_page)
                R.id.cartFragment -> getString(R.string.cart_page)
                R.id.productFragment -> getString(R.string.product_description)
                R.id.accountFragment -> getString(R.string.account_page)
                R.id.loginFragment -> getString(R.string.logging_page)
                R.id.registerFragment -> getString(R.string.registing_page)
                else -> getString(R.string.blank)
            }
            // show search menu item if fragment is main
            binding.topAppBar.menu.findItem(R.id.search).isVisible =
                navDestination.id == R.id.mainFragment
        }

        // set top bar onClick listeners
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
                R.id.more -> {
                    // Handle more item (inside overflow menu) press
                    true
                }
                else -> false
            }
        }
    }
}
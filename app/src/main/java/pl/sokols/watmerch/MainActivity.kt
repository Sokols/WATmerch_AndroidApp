package pl.sokols.watmerch

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import pl.sokols.watmerch.ui.account.AccountFragment
import pl.sokols.watmerch.ui.cart.CartFragment
import pl.sokols.watmerch.ui.main.MainFragment


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.main_fragment_container, MainFragment.newInstance())
                .commitNow()
        }

        val bottomNavigation: BottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    override fun onBackPressed() {
        AlertDialog.Builder(this)
            .setMessage("Are you sure you want to exit?")
            .setCancelable(false)
            .setPositiveButton(
                "Yes"
            ) { _, _ -> super@MainActivity.onBackPressed() }
            .setNegativeButton("No", null)
            .show()
    }

    private val mOnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.main_page -> {
                    val mainFragment = MainFragment.newInstance()
                    openFragment(mainFragment)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.cart_page -> {
                    val cartFragment = CartFragment.newInstance()
                    openFragment(cartFragment)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.account_page -> {
                    val accountFragment = AccountFragment.newInstance()
                    openFragment(accountFragment)
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }

    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.main_fragment_container, fragment)
        transaction.commit()
    }
}
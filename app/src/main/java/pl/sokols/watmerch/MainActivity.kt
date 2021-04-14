package pl.sokols.watmerch

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.main_activity.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        bottom_navigation.setupWithNavController(
            Navigation.findNavController(
                this,
                R.id.nav_host_fragment
            )
        )
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
}
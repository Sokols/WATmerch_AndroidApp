package pl.sokols.watmerch.utils

import android.app.Activity
import android.view.View
import com.google.android.material.snackbar.Snackbar
import pl.sokols.watmerch.R

class Utils {

    companion object {
        const val PRODUCT_ITEM_KEY: String = "merch_item_key"

        fun getSnackbar(view: View, message: String, activity: Activity): Snackbar {
            val snackbar = Snackbar.make(
                view,
                message,
                Snackbar.LENGTH_SHORT
            )
            snackbar.anchorView = activity.findViewById(R.id.bottom_navigation)
            return snackbar
        }
    }

}
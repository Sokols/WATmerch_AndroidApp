package pl.sokols.watmerch.utils

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.View
import com.google.android.material.snackbar.Snackbar
import pl.sokols.watmerch.R
import java.util.*

class Utils {

    companion object {
        const val SHARED_PREFERENCES_KEY: String = "shared_preferences_key"
        const val PRODUCT_BARCODE: String = "merch_item_key"

        fun getSnackbar(view: View, message: String, activity: Activity): Snackbar {
            val snackbar = Snackbar.make(
                view,
                message,
                Snackbar.LENGTH_SHORT
            )
            snackbar.anchorView = activity.findViewById(R.id.bottom_navigation)
            return snackbar
        }

        fun getBitmapFromString(string: String?): Bitmap? {
            val imageBytes = Base64.getDecoder().decode(string)
            return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
        }
    }

}
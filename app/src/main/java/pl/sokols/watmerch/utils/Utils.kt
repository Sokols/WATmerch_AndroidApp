package pl.sokols.watmerch.utils

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.view.View
import com.google.android.material.snackbar.Snackbar
import pl.sokols.watmerch.R
import java.text.SimpleDateFormat
import java.util.*

class Utils {

    companion object {
        const val CHOOSE_CAMERA: String = "choose_camera"
        const val CHOOSE_GALLERY: String = "choose_gallery"
        const val PARENT_COMPONENT_ID: String = "parent_component_id"
        const val SHARED_PREFERENCES_KEY: String = "shared_preferences_key"
        const val PRODUCT_BARCODE: String = "merch_item_key"
        private const val DATE_FORMAT: String = "yyyy-MM-dd"

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

        fun getDateStringFromString(birthDate: String?): String? {
            if (birthDate != null) {
                val format = SimpleDateFormat(DATE_FORMAT, Locale.ENGLISH)
                val date =  format.parse(birthDate)
                return format.format(date)
            }
            return null
        }

        fun getStringFromDate(birthDate: Date?): String? {
            if (birthDate != null) {
                val format = SimpleDateFormat(DATE_FORMAT, Locale.ENGLISH)
                return format.format(birthDate)
            }
            return null
        }
    }
}
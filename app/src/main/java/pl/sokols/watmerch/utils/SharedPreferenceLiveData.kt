package pl.sokols.watmerch.utils

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData

class SharedPreferenceLiveData(private val sharedPreferences: SharedPreferences) :
    LiveData<HashSet<Int>>() {

    private val KEY = AppPreferences.Key.CART_PRODUCTS_BARCODES.toString()

    private val mTokenSharedPreferenceListener =
        SharedPreferences.OnSharedPreferenceChangeListener { _: SharedPreferences?, key: String? ->
            when (key) {
                KEY -> value = AppPreferences.cartProductsBarcodes
            }
        }

    override fun onActive() {
        super.onActive()
        value = AppPreferences.cartProductsBarcodes
        sharedPreferences.registerOnSharedPreferenceChangeListener(mTokenSharedPreferenceListener)
    }

    override fun onInactive() {
        super.onInactive()
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(mTokenSharedPreferenceListener)
    }
}
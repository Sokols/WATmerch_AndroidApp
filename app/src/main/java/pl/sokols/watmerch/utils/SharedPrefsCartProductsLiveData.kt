package pl.sokols.watmerch.utils

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedPrefsCartProductsLiveData @Inject constructor(
    private val prefs: AppPreferences
) : LiveData<HashSet<Int>>() {

    private val CART_PRODUCTS_KEY = AppPreferences.Key.CART_PRODUCTS_BARCODES.toString()

    private val mTokenSharedPreferenceListener =
        SharedPreferences.OnSharedPreferenceChangeListener { _: SharedPreferences?, key: String? ->
            when (key) {
                CART_PRODUCTS_KEY -> value = prefs.cartProductsBarcodes
            }
        }

    override fun onActive() {
        super.onActive()
        value = prefs.cartProductsBarcodes
        prefs.prefs.registerOnSharedPreferenceChangeListener(mTokenSharedPreferenceListener)
    }

    override fun onInactive() {
        super.onInactive()
        prefs.prefs.unregisterOnSharedPreferenceChangeListener(mTokenSharedPreferenceListener)
    }
}
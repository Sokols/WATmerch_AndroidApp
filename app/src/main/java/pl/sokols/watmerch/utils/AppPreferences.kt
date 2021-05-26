package pl.sokols.watmerch.utils

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppPreferences @Inject constructor(@ApplicationContext context: Context) {
    var prefs: SharedPreferences =
        context.getSharedPreferences(Utils.SHARED_PREFERENCES_KEY, MODE_PRIVATE)

    var authToken: String?
        get() = getString(Key.AUTH_TOKEN.toString())
        set(value) = setString(Key.AUTH_TOKEN.toString(), value)

    var cookies: HashSet<String>?
        get() = getStringSet(Key.COOKIES.toString())
        set(value) = setStringSet(Key.COOKIES.toString(), value)

    var cartProductsBarcodes: HashSet<Int>?
        get() = convertStringHashSetToIntegerHashSet(
            getStringSet(Key.CART_PRODUCTS_BARCODES.toString())
        )
        set(value) = setStringSet(
            Key.CART_PRODUCTS_BARCODES.toString(),
            convertIntHashSetToStringHashSet(value)
        )

    var userUsername: String?
        get() = getString(Key.USER_USERNAME.toString())
        set(value) = setString(Key.USER_USERNAME.toString(), value)

    var userPassword: String?
        get() = getString(Key.USER_PASSWORD.toString())
        set(value) = setString(Key.USER_PASSWORD.toString(), value)

    var language: String?
        get() = getString(Key.LANGUAGE.toString())
        set(value) = setString(Key.LANGUAGE.toString(), value)

    var theme: String?
        get() = getString(Key.THEME.toString())
        set(value) = setString(Key.THEME.toString(), value)

    enum class Key {
        AUTH_TOKEN, COOKIES, CART_PRODUCTS_BARCODES, USER_USERNAME, USER_PASSWORD, LANGUAGE, THEME;
    }

    fun remove(name: String): SharedPreferences.Editor = prefs.edit().remove(name)!!

    private fun getBoolean(name: String): Boolean? = if (prefs.contains(name)) prefs.getBoolean(name, false) else null
    private fun getFloat(name: String): Float? = if (prefs.contains(name)) prefs.getFloat(name, 0f) else null
    private fun getInt(name: String): Int? = if (prefs.contains(name)) prefs.getInt(name, 0) else null
    private fun getLong(name: String): Long? = if (prefs.contains(name)) prefs.getLong(name, 0) else null
    private fun getString(name: String): String? = if (prefs.contains(name)) prefs.getString(name, "") else null

    private fun getStringSet(name: String): HashSet<String>? = if (prefs.contains(name)) prefs.getStringSet(name, HashSet()) as HashSet<String>? else null
    private fun setBoolean(name: String, value: Boolean?) = value?.let { prefs.edit().putBoolean(name, value).apply() } ?: prefs.edit().remove(name).apply()
    private fun setFloat(name: String, value: Float?) = value?.let { prefs.edit().putFloat(name, value).apply() } ?: prefs.edit().remove(name).apply()
    private fun setInt(name: String, value: Int?) = value?.let { prefs.edit().putInt(name, value).apply() } ?: prefs.edit().remove(name).apply()
    private fun setLong(name: String, value: Long?) = value?.let { prefs.edit().putLong(name, value).apply() } ?: prefs.edit().remove(name).apply()
    private fun setString(name: String, value: String?) = value?.let { prefs.edit().putString(name, value).apply() } ?: prefs.edit().remove(name).apply()
    private fun setStringSet(name: String, value: HashSet<String>?) = value?.let { prefs.edit().putStringSet(name, value).apply() } ?: prefs.edit().remove(name).apply()

    private fun convertStringHashSetToIntegerHashSet(hashSet: HashSet<String>?): HashSet<Int> {
        val intHashSet: HashSet<Int> = HashSet()
        if (hashSet != null) {
            for (string: String in hashSet) {
                intHashSet.add(string.toInt())
            }
        }
        return intHashSet
    }

    private fun convertIntHashSetToStringHashSet(hashSet: HashSet<Int>?): HashSet<String> {
        val stringHashSet: HashSet<String> = HashSet()
        if (hashSet != null) {
            for (int: Int in hashSet) {
                stringHashSet.add(int.toString())
            }
        }
        return stringHashSet
    }
}
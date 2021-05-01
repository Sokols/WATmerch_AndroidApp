package pl.sokols.watmerch.utils

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import androidx.core.content.edit

object AppPreferences {
    var sharedPreferences: SharedPreferences? = null

    fun setup(context: Context) {
        sharedPreferences = context.getSharedPreferences(Utils.SHARED_PREFERENCES_KEY, MODE_PRIVATE)
    }

    var authToken: String?
        get() = Key.AUTH_TOKEN.getString()
        set(value) = Key.AUTH_TOKEN.setString(value)

    var cookies: HashSet<String>?
        get() = Key.COOKIES.getStringSet()
        set(value) = Key.COOKIES.setStringSet(value)

    var cartProductsBarcodes: HashSet<Int>?
        get() = convertStringHashSetToIntegerHashSet(Key.CART_PRODUCTS_BARCODES.getStringSet())
        set(value) = Key.CART_PRODUCTS_BARCODES.setStringSet(convertIntHashSetToStringHashSet(value))

    var userUsername: String?
        get() = Key.USER_USERNAME.getString()
        set(value) = Key.USER_USERNAME.setString(value)

    var userPassword: String?
        get() = Key.USER_PASSWORD.getString()
        set(value) = Key.USER_PASSWORD.setString(value)

    enum class Key {
        AUTH_TOKEN, COOKIES, CART_PRODUCTS_BARCODES, USER_USERNAME, USER_PASSWORD;

        fun getBoolean(): Boolean? = if (sharedPreferences!!.contains(name)) sharedPreferences!!.getBoolean(name, false) else null
        fun getFloat(): Float? = if (sharedPreferences!!.contains(name)) sharedPreferences!!.getFloat(name, 0f) else null
        fun getInt(): Int? = if (sharedPreferences!!.contains(name)) sharedPreferences!!.getInt(name, 0) else null
        fun getLong(): Long? = if (sharedPreferences!!.contains(name)) sharedPreferences!!.getLong(name, 0) else null
        fun getString(): String? = if (sharedPreferences!!.contains(name)) sharedPreferences!!.getString(name, "") else null
        fun getStringSet(): HashSet<String>? = if (sharedPreferences!!.contains(name)) sharedPreferences!!.getStringSet(name, HashSet()) as HashSet<String>? else null

        fun setBoolean(value: Boolean?) = value?.let { sharedPreferences!!.edit { putBoolean(name, value) } } ?: remove()
        fun setFloat(value: Float?) = value?.let { sharedPreferences!!.edit { putFloat(name, value) } } ?: remove()
        fun setInt(value: Int?) = value?.let { sharedPreferences!!.edit { putInt(name, value) } } ?: remove()
        fun setLong(value: Long?) = value?.let { sharedPreferences!!.edit { putLong(name, value) } } ?: remove()
        fun setString(value: String?) = value?.let { sharedPreferences!!.edit { putString(name, value) } } ?: remove()
        fun setStringSet(value: HashSet<String>?) = value?.let { sharedPreferences!!.edit { putStringSet(name, value) } } ?: remove()

        fun remove() = sharedPreferences!!.edit { remove(name) }
    }

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
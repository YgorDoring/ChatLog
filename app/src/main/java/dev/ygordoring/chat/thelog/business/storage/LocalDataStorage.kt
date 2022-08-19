package dev.ygordoring.chat.thelog.business.storage

import android.content.Context
import android.content.SharedPreferences

class LocalDataStorage(val context: Context) : StorageContract {
    companion object {
        const val PREFS_NAME = "theLog"
    }

    private val prefs: SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    override fun saveString(key: String, text: String): Boolean {
        with(prefs.edit()) {
            putString(key, text)
            return commit()
        }
    }

    override fun saveInt(key: String, value: Int): Boolean {
        with(prefs.edit()) {
            putInt(key, value)
            return commit()
        }
    }

    override fun saveBoolean(key: String, status: Boolean): Boolean {
        with(prefs.edit()) {
            putBoolean(key, status)
            return commit()
        }
    }

    override fun getString(key: String): String? {
        return prefs.getString(key, null)
    }

    override fun getInt(key: String): Int {
        return prefs.getInt(key, 0)
    }

    override fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return prefs.getBoolean(key, defaultValue)
    }

    override fun clearAllData() {
        with(prefs.edit()) {
            clear()
            commit()
        }
    }

    override fun deleteValue(key: String) {
        with(prefs.edit()) {
            remove(key)
            commit()
        }
    }
}
package dev.ygordoring.chat.thelog.business.storage

interface StorageContract {
    fun saveString(key: String, text: String): Boolean

    fun saveInt(key: String, value: Int): Boolean

    fun saveBoolean(key: String, status: Boolean): Boolean

    fun getString(key: String): String?

    fun getInt(key: String): Int

    fun getBoolean(key: String, defaultValue: Boolean): Boolean

    fun clearAllData()

    fun deleteValue(key: String)
}
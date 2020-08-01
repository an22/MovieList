package com.an2.local.app_preferences

interface AppPreferences {

    fun putInt(key: String, value: Int)
    fun getInt(key: String, def: Int = -1): Int

    fun putBoolean(key: String, value: Boolean)
    fun getBoolean(key: String, def: Boolean = false): Boolean

    fun putString(key: String, value: String)
    fun getString(key: String, def: String = ""): String

}
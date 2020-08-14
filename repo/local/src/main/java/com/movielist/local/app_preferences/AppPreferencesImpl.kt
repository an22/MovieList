package com.movielist.local.app_preferences

import android.content.Context
import androidx.core.content.edit
import javax.inject.Inject
import javax.inject.Singleton

private const val PREFERENCES_NAME = "MegaMarketAppPrefs"

@Singleton
class AppPreferencesImpl @Inject constructor(context: Context) : AppPreferences {
    private val storage = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)

    override fun putInt(key: String, value: Int) {
        storage.edit { putInt(key, value) }
    }

    override fun getInt(key: String, def: Int): Int {
        return storage.getInt(key, def)
    }

    override fun putBoolean(key: String, value: Boolean) {
        storage.edit { putBoolean(key, value) }
    }

    override fun getBoolean(key: String, def: Boolean): Boolean {
        return storage.getBoolean(key, def)
    }

    override fun putString(key: String, value: String) {
        storage.edit { putString(key, value) }
    }

    override fun getString(key: String, def: String): String {
        return storage.getString(key, def).orEmpty()
    }
}
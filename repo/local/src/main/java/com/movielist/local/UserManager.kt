package com.movielist.local

import com.movielist.local.app_preferences.AppPreferences
import com.movielist.local.app_preferences.PreferencesKeys
import com.movielist.local.database.dao.UserDao
import com.movielist.local.database.entity.UserEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserManager @Inject constructor(
        private val appPreferences: AppPreferences,
        private val userDao: UserDao
) {

    fun setUserAuthToken(token: String) {
        appPreferences.putString(PreferencesKeys.USER_AUTH_TOKEN, token)
    }

    fun getUserAuthToken(): String {
        return appPreferences.getString(PreferencesKeys.USER_AUTH_TOKEN)
    }

    fun setUserAnonymous() {
        appPreferences.putBoolean(PreferencesKeys.USER_AUTH_ANONYMOUS, true)
    }

    fun isUserAnonymous(): Boolean {
        return appPreferences.getBoolean(PreferencesKeys.USER_AUTH_ANONYMOUS, false)
    }

    suspend fun isUserAuthorized(): Boolean {
        return withContext(Dispatchers.IO) {
            userDao.getUser() != null && getUserAuthToken().isNotEmpty()
        }
    }

    suspend fun setUserProfile(user: UserEntity) {
        withContext(Dispatchers.IO) {
            userDao.removeAppUser()
            userDao.setUser(user)
        }
    }

    suspend fun getUserProfile(): UserEntity? {
        return withContext(Dispatchers.IO) {
            userDao.getUser()
        }
    }
}
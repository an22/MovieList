package com.an2.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.an2.local.database.AppDatabase.Companion.DATABASE_VERSION
import com.an2.local.database.dao.UserDao
import com.an2.local.database.entity.UserEntity

@Database(
        entities = [UserEntity::class],
        version = DATABASE_VERSION,
        exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {
        const val DATABASE_NAME = "MovieListDatabase"
        const val DATABASE_VERSION = 1
    }
}
package com.movielist.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.movielist.local.database.AppDatabase.Companion.DATABASE_VERSION
import com.movielist.local.database.dao.UserDao
import com.movielist.local.database.entity.UserEntity

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
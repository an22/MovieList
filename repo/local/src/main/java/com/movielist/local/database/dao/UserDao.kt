package com.movielist.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.movielist.local.database.entity.UserEntity

@Dao
abstract class UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun setUser(userEntity: UserEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun updateUser(userEntity: UserEntity)

    @Query("select * from user limit 1")
    abstract suspend fun getUser(): UserEntity?

    @Query("delete from user")
    abstract suspend fun removeAppUser()
}
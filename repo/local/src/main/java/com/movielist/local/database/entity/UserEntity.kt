package com.movielist.local.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
        tableName = "user"
)
data class UserEntity(
        @PrimaryKey
        val id: Long = 0,
        val language: String,
        val country: String,
        val name: String,
        val username: String,
        val adult: Boolean = false
)
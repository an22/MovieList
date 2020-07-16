package com.an2.domain.entity

data class User(
        val id: Long = 0,
        val language: String,
        val country: String,
        val name: String,
        val username: String,
        val adult: Boolean = false
)
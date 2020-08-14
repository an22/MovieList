package com.movielist.core.base.data.network

import com.movielist.core.R

object BaseValidator {

    const val UNKNOWN_HOST = "unknown_host"

    fun localizeErrorText(errorText: String): Int {
        return when(errorText){
            UNKNOWN_HOST -> R.string.error_server_no_internet_connection
            else -> R.string.error_server_internal
        }
    }
}
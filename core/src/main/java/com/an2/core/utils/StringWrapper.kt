package com.an2.core.utils

import android.content.Context
import androidx.annotation.StringRes

class StringWrapper {
    private val message: String?
    @StringRes
    private val messageRes: Int?

    constructor(message: String) {
        this.message = message
        this.messageRes = null
    }

    constructor(@StringRes messageRes: Int) {
        this.message = null
        this.messageRes = messageRes
    }

    fun getString(context: Context): String {
        return message ?: context.getString(messageRes!!)
    }
}
package com.movielist.core.common.base.presentation.viewmodel

import android.content.Context
import androidx.annotation.StringRes

interface Event


abstract class BaseMessageEvent : Event {
    @StringRes
    private val messageId: Int
    private val message: String?

    constructor(@StringRes messageId: Int) {
        this.messageId = messageId
        this.message = null
    }

    constructor(message: String) {
        this.messageId = 0
        this.message = message
    }

    fun getMessageString(context: Context): String = message ?: context.getString(messageId)
}

class MessageEvent : BaseMessageEvent {
    constructor(@StringRes messageId: Int) : super(messageId = messageId)
    constructor(message: String) : super(message = message)
}

class ErrorMessageEvent : BaseMessageEvent {
    constructor(@StringRes messageId: Int) : super(messageId = messageId)
    constructor(message: String) : super(message = message)
}

class AlertMessageEvent : BaseMessageEvent {
    var onDismissListener: () -> Unit = {}
        private set

    constructor(@StringRes messageId: Int, onDismissListener: () -> Unit = {}) : super(messageId = messageId) {
        this.onDismissListener = onDismissListener
    }

    constructor(message: String, onDismissListener: () -> Unit = {}) : super(message = message) {
        this.onDismissListener = onDismissListener
    }
}
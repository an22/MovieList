package com.movielist.core.base.domain.error_handler

interface ErrorHandler {
    fun toErrorEntity(throwable: Throwable): ErrorEntity
}
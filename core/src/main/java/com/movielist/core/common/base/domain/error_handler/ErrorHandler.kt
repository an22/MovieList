package com.movielist.core.common.base.domain.error_handler

interface ErrorHandler {
    fun toErrorEntity(throwable: Throwable): ErrorEntity
}
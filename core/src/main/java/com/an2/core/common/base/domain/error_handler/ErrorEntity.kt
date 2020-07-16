package com.an2.core.common.base.domain.error_handler

import com.an2.core.common.base.data.network.BaseValidator

sealed class ErrorEntity {

    open fun toErrorMessage() = this::class.java.simpleName

    sealed class ApiError : ErrorEntity() {
        object NetworkError : ApiError()
        object InternalServerError : ApiError()
        object ServiceUnavailable : ApiError()
        object NotFoundError : ApiError()
        object UnauthorizedError : ApiError()

        class MessageError(val message: String) : ApiError() {
            override fun toErrorMessage() = message
        }
    }

    object ProductNotFound : ErrorEntity()

    //todo: add some error type
    object NotConnectionError : ErrorEntity() {
        override fun toErrorMessage() = BaseValidator.UNKNOWN_HOST
    }

    class NotImplementedError(val throwable: Throwable? = null) : ErrorEntity()
    object UnknownError : ErrorEntity()
}

package com.an2.core.common.base.domain.error_handler

import retrofit2.HttpException
import java.net.HttpURLConnection
import java.net.UnknownHostException
import javax.inject.Inject

class AppErrorHandler @Inject constructor() : ErrorHandler {
    override fun toErrorEntity(throwable: Throwable): ErrorEntity {
        return when (throwable) {
            is HttpException -> fromHttpExceptionCode(throwable.code())
            is UnknownHostException -> ErrorEntity.NotConnectionError
            else -> ErrorEntity.NotImplementedError(throwable)
        }
    }

    private fun fromHttpExceptionCode(code: Int): ErrorEntity {
        return when (code) {
            HttpURLConnection.HTTP_UNAUTHORIZED -> ErrorEntity.ApiError.UnauthorizedError
            HttpURLConnection.HTTP_UNAVAILABLE -> ErrorEntity.ApiError.ServiceUnavailable
            HttpURLConnection.HTTP_INTERNAL_ERROR -> ErrorEntity.ApiError.InternalServerError
            HttpURLConnection.HTTP_NOT_FOUND -> ErrorEntity.ApiError.NotFoundError
            HttpURLConnection.HTTP_NOT_IMPLEMENTED -> ErrorEntity.NotImplementedError()
            HttpURLConnection.HTTP_CLIENT_TIMEOUT -> ErrorEntity.ApiError.NetworkError
            else -> ErrorEntity.UnknownError
        }
    }
}
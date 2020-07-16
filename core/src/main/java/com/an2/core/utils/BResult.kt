package com.an2.core.utils

import com.an2.core.common.base.domain.error_handler.ErrorEntity

/**
 * Class wrapper describing successful or unsuccessful state
 * Example: for network request result
 */
sealed class BResult<out T : Any> {
    data class Success<out T : Any>(val data: T) : BResult<T>()
    data class Error(val error: ErrorEntity) : BResult<Nothing>()
}

inline fun <T : Any> BResult<T>.ifError(handler: (T: ErrorEntity) -> Unit): BResult<T> {
    when (this) {
        is BResult.Error -> handler(this.error)
    }
    return this
}

inline fun <T : Any> BResult<T>.returnIfError(handler: (T: ErrorEntity) -> T): BResult<T> {
    return when (this) {
        is BResult.Error -> BResult.Success(handler(this.error))
        is BResult.Success -> this
    }
}

//todo: ifSuccess!!
inline fun <T : Any> BResult<T>.isSuccess(handler: (T) -> Unit): BResult<T> {
    when (this) {
        is BResult.Success -> handler(this.data)
    }
    return this
}

fun <T : Any> BResult<T>.isSuccess(): Boolean {
    return this is BResult.Success
}

fun <T : Any> BResult<T>.isError(): Boolean {
    return this is BResult.Error
}

fun <T : Any> BResult<T>.data(): T {
    return (this as BResult.Success).data
}

fun <T : Any> BResult<T>.errorEntity(): ErrorEntity {
    return (this as BResult.Error).error
}

inline fun <Input : Any, Output : Any> BResult<Input>.map(mapper: (Input) -> Output): BResult<Output> {
    return when (this) {
        is BResult.Success -> BResult.Success(mapper.invoke(this.data))
        is BResult.Error -> BResult.Error(this.error)
    }
}

inline fun <Input : Any, Output : Any> BResult<Input>.mapBResult(mapper: (Input) -> BResult<Output>): BResult<Output> {
    return when (this) {
        is BResult.Success -> mapper.invoke(this.data)
        is BResult.Error -> BResult.Error(this.error)
    }
}
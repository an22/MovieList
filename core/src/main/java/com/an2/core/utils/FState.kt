package com.an2.core.utils

sealed class FState<out T : Any> {
    object None : FState<Nothing>()
    object Loading : FState<Nothing>()
    class Result<R : Any>(val result: BResult<R>) : FState<R>()
}
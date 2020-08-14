package com.movielist.core.extensions

fun Long?.orIncorrect() = this ?: -1

fun Short?.asBool() = this == 1.toShort()

fun Int?.orZero() = this ?: 0

fun Int?.orNegative() = this ?: -1

fun Float?.orNegative() = this ?: -1f

fun Float?.orZero() = this ?: 0f
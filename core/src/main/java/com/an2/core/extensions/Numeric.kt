package com.an2.core.extensions

fun Long?.orIncorrect() = this ?: -1

fun Short?.asBool() = this == 1.toShort()

fun Int?.orZero() = this ?: 0

fun Int?.orNegative() = this ?: -1

fun Float?.orNegative() = this ?: -1f


package com.an2.core.common.base.domain.entity

interface Dto<out T> {
    fun convert(): T
}

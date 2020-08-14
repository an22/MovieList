package com.movielist.core.common.base.domain.entity

interface Dto<out T> {
    fun convert(): T
}

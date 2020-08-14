package com.movielist.core.base.data.network

open class BaseResponse<Data>(
    val data: Data? = null,
    val msgText: String = "",
    val errText: String = ""
)
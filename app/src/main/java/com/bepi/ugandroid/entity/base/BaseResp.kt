package com.bepi.ugandroid.entity.base

data class BaseResp<T>(
    val code: Int = 0,
    val msg: String = "",
    val data: T,
    val success: Boolean
)
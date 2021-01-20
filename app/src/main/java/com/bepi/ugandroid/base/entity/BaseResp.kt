package com.bepi.ugandroid.base.entity

data class BaseResp<T>(
    val code: Int = 0,
    val msg: String = "",
    val data: T,
    val success: Boolean
)
package com.bepi.ugandroid.api

import com.bepi.ugandroid.entity.base.BaseResp
import com.bepi.ugandroid.entity.resp.LoginResp
import okhttp3.Call
import okhttp3.Response
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.http.Query

interface AppService {

    @POST("/screen/login")
    @FormUrlEncoded
    suspend fun login(@Query("iemi") iemi: String): BaseResp<LoginResp>
}
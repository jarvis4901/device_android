package com.bepi.ugandroid.entity.resp

data class LoginResp(
    private var token: String? = null,
    val cabinetIdAuto: Int = 0,
    val cabinetNumber: String,
    val screenSize: Int = 0,
    val cabinetName: String,
    val cabinetAddress: String,
    val servicePhone: String,
    val wifiStatus: Int = 0,
    val wifiName: String,
    val wifiPassword: String,
    val hint: String,
    val mailSetOpenStatus: String,
    val advertisingInfos: List<BannerResp>?,
)

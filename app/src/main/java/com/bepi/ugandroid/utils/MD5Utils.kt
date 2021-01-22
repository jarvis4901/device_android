package com.bepi.ugandroid.utils

import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import kotlin.experimental.and


fun md5(string: String): String? {
    if (string.isEmpty()) {
        return ""
    }
    var md5: MessageDigest? = null
    try {
        md5 = MessageDigest.getInstance("MD5")
        val bytes = md5.digest(string.toByteArray())
        val result = StringBuilder()
        for (b in bytes) {
            var temp = Integer.toHexString((b and 0xff.toByte()).toInt())
            if (temp.length == 1) {
                temp = "0$temp"
            }
            result.append(temp)
        }
        return result.toString()
    } catch (e: NoSuchAlgorithmException) {
        e.printStackTrace()
    }
    return ""
}

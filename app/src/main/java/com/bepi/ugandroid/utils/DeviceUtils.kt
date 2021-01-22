package com.bepi.ugandroid.utils

import java.lang.reflect.InvocationTargetException

// 获取设备序列号
fun getDeviceSerial(): String? {
    var serial = "unknown"
    try {
        val clazz = Class.forName("android.os.Build")
        val paraTypes = Class.forName("java.lang.String")
        val method = clazz.getDeclaredMethod("getString", paraTypes)
        if (!method.isAccessible) {
            method.isAccessible = true
        }
        serial = method.invoke(null, "ro.serialno") as String
    } catch (e: ClassNotFoundException) {
        e.printStackTrace()
    } catch (e: NoSuchMethodException) {
        e.printStackTrace()
    } catch (e: InvocationTargetException) {
        e.printStackTrace()
    } catch (e: IllegalAccessException) {
        e.printStackTrace()
    }
    return serial
}
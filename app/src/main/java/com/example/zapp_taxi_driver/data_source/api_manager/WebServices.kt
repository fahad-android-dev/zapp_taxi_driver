package com.example.zapp_taxi_driver.data_source.api_manager

import com.example.zapp_taxi_driver.helper.Global


object WebServices {
    private val IsUrlType = if (Global.isTestModeEnabled) AppDomain.DEV else AppDomain.LIVE

    fun getDomainUrl(): String =
        when (IsUrlType) {
            AppDomain.LIVE -> ApiLive
            AppDomain.DEV -> ApiDev
        }

    private const val ApiDev = "http://68.183.92.60/Zap_taxi/api/"
    private const val ApiLive = "http://68.183.92.60/Zap_taxi/api/"

    const val RegisterWs = "Driver/registration"
    const val LoginWs = "mobileotp/login"

    fun getLoginUrl() : String {
        return LoginWs
    }

    fun getRegisterUrl() : String {
        return RegisterWs
    }

}

enum class AppDomain {
    LIVE, DEV
}
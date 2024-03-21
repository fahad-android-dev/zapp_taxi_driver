package com.example.zapp_taxi_driver.data_source.api_manager

import com.example.zapp_taxi_driver.helper.Global


object WebServices {
    private val IsUrlType = if (Global.isTestModeEnabled) AppDomain.DEV else AppDomain.LIVE

    fun getDomainUrl(): String =
        when (IsUrlType) {
            AppDomain.LIVE -> ApiLive
            AppDomain.DEV -> ApiDev
        }

    private const val ApiDev = "https://dev-api.openslot.co/api/v1/"
    private const val ApiLive = "https://dev-api.openslot.co/api/v1/"

    const val RegisterWs = "user/register?"
    const val LoginWs = "user/login?"

}

enum class AppDomain {
    LIVE, DEV
}
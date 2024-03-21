package com.example.zapp_taxi_driver.mvvm.login.model

 data class LoginRequestModel(
    val app_version: String? = null,
    val device_model: String? = null,
    val device_token: String? = null,
    val device_type: String? = null,
    val email: String? = null,
    val os_version: String? = null,
    val password: String? = null
)
package com.example.zapp_taxi_driver.mvvm.login.model

import kotlinx.serialization.Serializable

@Serializable
 data class LoginRequestModel(
    val mobile: String? = null,
    val password: String? = null
)
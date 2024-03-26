package com.example.zapp_taxi_driver.mvvm.home.model

import kotlinx.serialization.Serializable

@Serializable
data class UserProfileRequestModel(
    val id : String ?= null,
    val AuthToken : String ?= null,
)

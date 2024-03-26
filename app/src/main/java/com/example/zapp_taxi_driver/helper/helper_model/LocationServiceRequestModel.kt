package com.example.zapp_taxi_driver.helper.helper_model

import kotlinx.serialization.Serializable

@Serializable
data class LocationServiceRequestModel(
    val id : String ?= null,
    val lat : String ?= null,
    val long : String ?= null,
    val AuthToken : String ?= null,
    val fcm_no : String ?= null,
)

package com.example.zapp_taxi_driver.mvvm.home.model

import kotlinx.serialization.Serializable

@Serializable
data class AcceptRejectBookingRequestModel(
    val id : String ?= null,
    val AuthToken : String ?= null,
    val booking_id : String ?= null,
)
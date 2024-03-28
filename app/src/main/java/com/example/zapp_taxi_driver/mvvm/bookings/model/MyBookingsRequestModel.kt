package com.example.zapp_taxi_driver.mvvm.bookings.model

import kotlinx.serialization.Serializable

@Serializable
data class MyBookingsRequestModel(
    val AuthToken: String? = null,
    val id: Int? = null
)
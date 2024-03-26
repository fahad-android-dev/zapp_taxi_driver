package com.example.zapp_taxi_driver.helper.helper_model

import kotlinx.serialization.Serializable

@Serializable
data class LocationServiceResponseModel(
    val Payment_status: Int? = null,
    val available: Int? = null,
    val booking_local: List<String?>? = null,
    val code: Int? = null,
    val message: String? = null,
    val reason: List<String?>? = null,
    val setting: Setting? = null,
    val today_details: TodayDetails? = null,
    val total_booking: String? = null,
    val vehicle_id: String? = null,
    val vehicle_qr_code: String? = null,
    val vehicle_ride_type: String? = null,
    val vehicle_type: String? = null,
    val verified: Int? = null
)

@Serializable
data class Setting(
    val d_hard_code: String? = null,
    val d_hard_code_message: String? = null,
    val d_soft_code: String? = null,
    val d_soft_code_message: String? = null,
    val d_soft_hard_type: String? = null
)

@Serializable
data class TodayDetails(
    val today_booking: String? = null,
    val total_amt: String? = null
)
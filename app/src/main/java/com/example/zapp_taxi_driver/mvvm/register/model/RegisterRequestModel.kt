package com.example.zapp_taxi_driver.mvvm.register.model

import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequestModel(
    val name: String? = null,
    val mobile: String? = null,
    val email_id: String? = null,
    val language: String? = null,
    val vehicle_no: String? = null,
    val manufacturer: String? = null,
    val model: String? = null,
    val seating_capacity: String? = null,
    val vehicle_type: String? = null,
    val fuel_type: String? = null,
    val engine_capacity: String? = null,
    val profile: String? = null,
    val driving_licence: String? = null,
    val reg_cer: String? = null,
    val vehicle_insurance: String? = null,
    val vehicle_permit: String? = null,
    val password: String? = null
)

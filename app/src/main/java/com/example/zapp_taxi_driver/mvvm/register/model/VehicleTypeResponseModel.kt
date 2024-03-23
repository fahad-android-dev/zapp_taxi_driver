package com.example.zapp_taxi_driver.mvvm.register.model

import kotlinx.serialization.Serializable

@Serializable
data class VehicleTypeResponseModel(
    val code: String? = null,
    val message: String? = null,
    val `data`: ArrayList<VehicleTypeDataModel?>? = null,
)

@Serializable
data class VehicleTypeDataModel(
    val id: String? = null,
    val name: String? = null,
    val fuel_type: String? = null,
)

package com.example.zapp_taxi_driver.mvvm.driver_report.model

import kotlinx.serialization.Serializable

@Serializable
data class DriverReportRequestModel(
    val id  : String ?= null,
)

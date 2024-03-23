package com.example.zapp_taxi_driver.mvvm.driver_report.model

import kotlinx.serialization.Serializable

@Serializable
data class DriverReportResponseModel(
    val DriverDetails: ArrayList<DriverDetailDataModel?>? = null,
    val code: Int? = null,
    val message: String? = null
)

@Serializable
data class DriverDetailDataModel(
    val firstname: String? = null,
    val punchin_punchout_date: String? = null,
    val total_time: String? = null
)
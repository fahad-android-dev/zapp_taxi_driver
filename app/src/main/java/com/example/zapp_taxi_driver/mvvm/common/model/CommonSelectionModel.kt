package com.example.zapp_taxi_driver.mvvm.common.model

import kotlinx.serialization.Serializable

@Serializable
data class CommonSelectionModel(
    var name: String = "",
    var fuel_type: String = "",
    var isSelected: Boolean = false,
    var id: String = "",
):java.io.Serializable
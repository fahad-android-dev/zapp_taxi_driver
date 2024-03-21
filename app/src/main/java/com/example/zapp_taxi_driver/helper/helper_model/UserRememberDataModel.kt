package com.example.zapp_taxi_driver.helper.helper_model


data class UserRememberModel(
    val `data`: UserRememberDataModel? = null,
)

data class UserRememberDataModel(
    val emailOrPhone: String? = null,
    val password: String? = null,
    val isRemember: Boolean? = false
)

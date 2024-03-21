package com.example.zapp_taxi_driver.helper.helper_model

import kotlinx.serialization.Serializable

@Serializable
data class UserResponseModel(
    val AuthToken: String? = null,
    val code: String? = null,
    val message: String? = null,
    val user_id: String? = null
)

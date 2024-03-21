package com.example.zapp_taxi_driver.helper.helper_model

import kotlinx.serialization.Serializable

@Serializable
data class UserResponseModel(
    val `data`: UserDataModel? = null,
    var message: String? = null,
    var code: String? = null,
    val status: Int? = null,
    val success: Boolean? = null
)

@Serializable
data class UserDataModel(
    val appVersion: String? = null,
    val code: String? = null,
    val countryId: String? = null,
    val createdAt: String? = null,
    val deviceModel: String? = null,
    val deviceToken: String? = null,
    val deviceType: String? = null,
    val dob: String? = null,
    val email: String? = null,
    val firstName: String? = null,
    val gender: String? = null,
    val id: Int? = null,
    val image: String? = null,
    val isEmailVerified: Int? = null,
    val isPhoneVerified: Int? = null,
    val isSocialRegister: Int? = null,
    val lastName: String? = null,
    val newsletterSubscribed: Int? = null,
    val osVersion: String? = null,
    val phone: String? = null,
    val phoneCode: String? = null,
    val pushEnabled: Int? = null,
    val socialRegisterType: String? = null,
    var order_id : String = ""
)

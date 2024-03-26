package com.example.zapp_taxi_driver.helper.helper_model

import kotlinx.serialization.Serializable

@Serializable
data class UserProfileResponseModel(
    val code: Int? = null,
    val `data`: UserProfileDataModel? = null,
    val message: String? = null
)

@Serializable
data class UserProfileDataModel(
    val ac: String? = null,
    val address: String? = null,
    val address_proof: String? = null,
    val adhar_card: String? = null,
    val adhar_card_no: String? = null,
    val agent_code: String? = null,
    val airbag: String? = null,
    val defensive_driver_cert: String? = null,
    val driving_license: String? = null,
    val driving_license_expiry: String? = null,
    val driving_license_no: String? = null,
    val email_id: String? = null,
    val engine_capacity: String? = null,
    val firstname: String? = null,
    val fitness_certificate: String? = null,
    val fitness_certificate_expiry: String? = null,
    val fitness_certificate_img: String? = null,
    val fuel_type: String? = null,
    val insurance_expiry_date: String? = null,
    val insurance_img: String? = null,
    val isDeleted: String? = null,
    val language: String? = null,
    val lp: String? = null,
    val make: String? = null,
    val make_year: String? = null,
    val mobile: String? = null,
    val model: String? = null,
    val onw_mobile_no: String? = null,
    val owner_name: String? = null,
    val pcc_expiry_date: String? = null,
    val pcc_img: String? = null,
    val profile_image: String? = null,
    val r_certificate: String? = null,
    val r_certificate_no: String? = null,
    val rc_certificate_expiry: String? = null,
    val reg_address: String? = null,
    val registration_no: String? = null,
    val scroe: String? = null,
    val seating: String? = null,
    val taxi_permit: String? = null,
    val taxi_permit_expiry: String? = null,
    val vehicle_qr_code: String? = null,
    val vehicle_type: String? = null
)
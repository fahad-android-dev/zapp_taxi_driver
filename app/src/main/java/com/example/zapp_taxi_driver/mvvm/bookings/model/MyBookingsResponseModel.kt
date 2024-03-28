package com.example.zapp_taxi_driver.mvvm.bookings.model

import kotlinx.serialization.Serializable

@Serializable
data class MyBookingsResponseModel(
    val booking: ArrayList<MyBookingsDataModel?>? = null,
    val code: Int? = null,
    val message: String? = null
)

@Serializable
data class MyBookingsDataModel(
    val added_date: String? = null,
    val admin_status: String? = null,
    val amount: String? = null,
    val base_fare: String? = null,
    val behalf_book_user_id: String? = null,
    val booking_from: String? = null,
    val booking_otp: String? = null,
    val booking_type: String? = null,
    val cgst: String? = null,
    val city_id: String? = null,
    val commission: String? = null,
    val destination_address: String? = null,
    val destination_latitude: String? = null,
    val destination_longitude: String? = null,
    val distance: String? = null,
    val driver_id: String? = null,
    val end_time: String? = null,
    val feedback1: String? = null,
    val feedback2: String? = null,
    val final_amount: String? = null,
    val final_base_fare: String? = null,
    val final_cgst: String? = null,
    val final_commission: String? = null,
    val final_commission_db: String? = null,
    val final_destination_address: String? = null,
    val final_destination_latitude: String? = null,
    val final_destination_longitude: String? = null,
    val final_distance: String? = null,
    val final_gst_amt: String? = null,
    val final_minute: String? = null,
    val final_operator_commission: String? = null,
    val final_operator_commission_db: String? = null,
    val final_over_actual_rate_per_max_km_db: String? = null,
    val final_over_total_km_fare: String? = null,
    val final_parking_charge: String? = null,
    val final_pre_actual_minimum_km_db: String? = null,
    val final_pre_actual_rate_per_km_db: String? = null,
    val final_pre_total_km_fare: String? = null,
    val final_ride_time_fare_db: String? = null,
    val final_sgst: String? = null,
    val final_source_address: String? = null,
    val final_source_latitude: String? = null,
    val final_source_longitude: String? = null,
    val final_toll_charge: String? = null,
    val final_total_amt: String? = null,
    val final_total_km_charge: String? = null,
    val final_total_time_fare: String? = null,
    val given_km: String? = null,
    val google_km: String? = null,
    val gst_amt: String? = null,
    val id: String? = null,
    val isDeleted: String? = null,
    val leaverge_percent: String? = null,
    val minutes: String? = null,
    val mobile_no: String? = null,
    val name: String? = null,
    val offline_date_time: String? = null,
    val offline_paid_by_driver: String? = null,
    val operator_commission: String? = null,
    val operator_commission_db: String? = null,
    val over_actual_rate_per_max_km_db: String? = null,
    val over_total_km_fare: String? = null,
    val package_type: String? = null,
    val payment_id: String? = null,
    val pre_actual_minimum_km_db: String? = null,
    val pre_actual_rate_per_km_db: String? = null,
    val pre_total_km_fare: String? = null,
    val profit: String? = null,
    val razorpay_order_id: String? = null,
    val reason: String? = null,
    val ride_time_fare_db: String? = null,
    val sgst: String? = null,
    val source_address: String? = null,
    val source_latitude: String? = null,
    val source_longitude: String? = null,
    val start_time: String? = null,
    val status: String? = null,
    val total_amt: String? = null,
    val total_km_charge: String? = null,
    val total_time_fare: String? = null,
    val transaction_id: String? = null,
    val updated_date: String? = null,
    val user_id: String? = null,
    val vehicle_category: String? = null,
    val vehicle_id: String? = null,
    val vehicle_type: String? = null
)
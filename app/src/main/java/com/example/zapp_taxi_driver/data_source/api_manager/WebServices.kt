package com.example.zapp_taxi_driver.data_source.api_manager

import com.example.zapp_taxi_driver.helper.Global


object WebServices {
    private val IsUrlType = if (Global.isTestModeEnabled) AppDomain.DEV else AppDomain.LIVE

    fun getDomainUrl(): String =
        when (IsUrlType) {
            AppDomain.LIVE -> ApiLive
            AppDomain.DEV -> ApiDev
        }

    private const val ApiDev = "http://68.183.92.60/Zap_taxi/api/"
    private const val ApiLive = "http://68.183.92.60/Zap_taxi/api/"

    const val RegisterWs = "Driver/registration"
    const val LoginWs = "mobileotp/login"
    const val VehicleTypeWs = "User/get_vehicle_type"
    const val FuelTypeWs = "User/get_fuel_type"
    const val DriverDetailsWs = "Driver/DriverActiveDetails"
    const val UpdateLocationWs = "Driver/update_location"
    const val UserProfileWs = "User/userprofile"
    const val AcceptRideWs = "Driver/accept_ride"
    const val CancelRideWs = "Driver/cancel_ride"
    const val RejectRideWs = "Driver/reject_ride"
    const val MyBookingsWs = "Driver/my_booking"

    fun getLoginUrl() : String {
        return LoginWs
    }

    fun getRegisterUrl() : String {
        return RegisterWs
    }

    fun getVehicleTypeUrl() : String {
        return VehicleTypeWs
    }
    fun getFuelTypeUrl() : String {
        return FuelTypeWs
    }
    fun getDriverDetailsUrl() : String {
        return DriverDetailsWs
    }
    fun getUpdateLocationUrl() : String {
        return UpdateLocationWs
    }
    fun getUserProfileUrl() : String {
        return UserProfileWs
    }

    fun getAcceptRideUrl() : String {
        return AcceptRideWs
    }
    fun getCancelRideUrl() : String {
        return CancelRideWs
    }

    fun getRejectRideUrl() : String {
        return RejectRideWs
    }
    fun getMyBookingUrl() : String {
        return MyBookingsWs
    }


}

enum class AppDomain {
    LIVE, DEV
}
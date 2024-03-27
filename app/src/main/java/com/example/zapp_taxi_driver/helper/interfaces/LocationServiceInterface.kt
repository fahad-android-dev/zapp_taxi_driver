package com.example.zapp_taxi_driver.helper.interfaces

import com.example.zapp_taxi_driver.helper.helper_model.LocationServiceResponseModel

interface LocationServiceInterface {

    fun onApiResponseReceived(response: LocationServiceResponseModel) {}
}
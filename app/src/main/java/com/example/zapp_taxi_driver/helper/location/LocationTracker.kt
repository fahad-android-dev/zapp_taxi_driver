package com.example.zapp_taxi_driver.helper.location

import android.location.Location

interface LocationTracker {
    suspend fun getCurrentLocation(): Location?
}
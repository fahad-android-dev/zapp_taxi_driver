
package com.example.zapp_taxi_driver.helper.location

import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.zapp_taxi_driver.R
import com.example.zapp_taxi_driver.data_source.api_manager.ApiRepository
import com.example.zapp_taxi_driver.data_source.api_manager.WebServices
import com.example.zapp_taxi_driver.helper.PrefUtils.getUserDataResponse
import com.example.zapp_taxi_driver.helper.PrefUtils.getUserId
import com.example.zapp_taxi_driver.helper.helper_model.LocationServiceRequestModel
import com.example.zapp_taxi_driver.helper.helper_model.LocationServiceResponseModel
import com.example.zapp_taxi_driver.helper.helper_model.UserProfileResponseModel
import com.example.zapp_taxi_driver.mvvm.home.model.UserProfileRequestModel
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class LocationService: Service() {

    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private lateinit var locationClient: LocationClient
    private val scope = CoroutineScope(Dispatchers.IO)

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        locationClient = DefaultLocationClient(
            applicationContext,
            LocationServices.getFusedLocationProviderClient(applicationContext)
        )
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when(intent?.action) {
            ACTION_START -> start()
            ACTION_STOP -> stop()
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun start() {
        val notification = NotificationCompat.Builder(this, "location")
            .setContentTitle("Tracking location...")
            .setSmallIcon(R.mipmap.ic_launcher_new)
            .setOngoing(true)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        locationClient
            .getLocationUpdates(20000L)
            .catch { e -> e.printStackTrace() }
            .onEach { location ->
                val lat = location.latitude.toString()
                val long = location.longitude.toString()

                val mutLocationResponse : MutableLiveData<LocationServiceResponseModel?> = MutableLiveData()

                val model = LocationServiceRequestModel(
                    id = applicationContext.getUserId(),
                    lat = lat, long = long, AuthToken = applicationContext.getUserDataResponse()?.AuthToken, fcm_no = null

                )

                scope.launch(Dispatchers.IO + CoroutineExceptionHandler { _, throwable ->
                    throwable.printStackTrace()
                    mutLocationResponse.postValue(LocationServiceResponseModel(code = 0 , message = throwable.localizedMessage))
                }){
                    try {
                        ApiRepository
                            .apiPost<LocationServiceRequestModel, LocationServiceResponseModel>(
                                WebServices.getUpdateLocationUrl() , model)
                            .collectLatest {
                                mutLocationResponse.postValue(it)
                            }
                    }catch (e:Exception){
                        mutLocationResponse.postValue(LocationServiceResponseModel(code = 0 , message = e.localizedMessage))
                    }
                }
            }
            .launchIn(serviceScope)

        startForeground(1, notification.build())
    }

    private fun stop() {
        stopForeground(true)
        stopSelf()
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel()
    }

    companion object {
        const val ACTION_START = "ACTION_START"
        const val ACTION_STOP = "ACTION_STOP"
    }
}

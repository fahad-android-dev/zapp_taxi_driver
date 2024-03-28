package com.example.zapp_taxi_driver.mvvm.bookings.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zapp_taxi_driver.data_source.api_manager.ApiRepository
import com.example.zapp_taxi_driver.data_source.api_manager.WebServices
import com.example.zapp_taxi_driver.mvvm.bookings.adapter.MyBookingsAdapter
import com.example.zapp_taxi_driver.mvvm.bookings.model.MyBookingsDataModel
import com.example.zapp_taxi_driver.mvvm.bookings.model.MyBookingsRequestModel
import com.example.zapp_taxi_driver.mvvm.bookings.model.MyBookingsResponseModel
import com.example.zapp_taxi_driver.mvvm.driver_report.model.DriverDetailDataModel
import com.example.zapp_taxi_driver.mvvm.driver_report.model.DriverReportRequestModel
import com.example.zapp_taxi_driver.mvvm.driver_report.model.DriverReportResponseModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MyBookingsViewModel : ViewModel() {

    var arrListMyBookings = ArrayList<MyBookingsDataModel?>()
    var adapterMyBookings = MyBookingsAdapter()

    fun updateMyBookingsAdapter() {
        adapterMyBookings.setData(arrListMyBookings)
    }

    val mutMyBookingsResponse : MutableLiveData<MyBookingsResponseModel?> = MutableLiveData()
    fun myBookingsApi(model : MyBookingsRequestModel){
        viewModelScope.launch(Dispatchers.IO + CoroutineExceptionHandler { _, throwable ->
            throwable.printStackTrace()
            mutMyBookingsResponse.postValue(MyBookingsResponseModel(code = 0 , message = throwable.localizedMessage))
        }){
            try {
                ApiRepository
                    .apiPost<MyBookingsRequestModel, MyBookingsResponseModel>(WebServices.getMyBookingUrl() , model)
                    .collectLatest {
                        mutMyBookingsResponse.postValue(it)
                    }
            }catch (e:Exception){
                mutMyBookingsResponse.postValue(MyBookingsResponseModel(code = 0, message = e.localizedMessage))
            }
        }
    }
}
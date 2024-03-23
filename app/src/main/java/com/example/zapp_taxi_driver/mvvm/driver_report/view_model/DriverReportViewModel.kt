package com.example.zapp_taxi_driver.mvvm.driver_report.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zapp_taxi_driver.data_source.api_manager.ApiRepository
import com.example.zapp_taxi_driver.data_source.api_manager.WebServices
import com.example.zapp_taxi_driver.helper.helper_model.UserResponseModel
import com.example.zapp_taxi_driver.mvvm.common.adapter.CommonSelectionAdapter
import com.example.zapp_taxi_driver.mvvm.common.model.CommonSelectionModel
import com.example.zapp_taxi_driver.mvvm.driver_report.adapter.DriverReportAdapter
import com.example.zapp_taxi_driver.mvvm.driver_report.model.DriverDetailDataModel
import com.example.zapp_taxi_driver.mvvm.driver_report.model.DriverReportRequestModel
import com.example.zapp_taxi_driver.mvvm.driver_report.model.DriverReportResponseModel
import com.example.zapp_taxi_driver.mvvm.login.model.LoginRequestModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class DriverReportViewModel : ViewModel() {

    var arrListDriverReport = ArrayList<DriverDetailDataModel?>()
    var adapterDriverReport = DriverReportAdapter()

    fun updateDriverReportAdapter() {
        adapterDriverReport.setData(arrListDriverReport)
    }

    val mutDriverReportResponse : MutableLiveData<DriverReportResponseModel?> = MutableLiveData()
    fun driverReportApi(model : DriverReportRequestModel){
        viewModelScope.launch(Dispatchers.IO + CoroutineExceptionHandler { _, throwable ->
            throwable.printStackTrace()
            mutDriverReportResponse.postValue(DriverReportResponseModel(code = 0 , message = throwable.localizedMessage))
        }){
            try {
                ApiRepository
                    .apiPost<DriverReportRequestModel, DriverReportResponseModel>(WebServices.getDriverDetailsUrl() , model)
                    .collectLatest {
                        mutDriverReportResponse.postValue(it)
                    }
            }catch (e:Exception){
                mutDriverReportResponse.postValue(DriverReportResponseModel(code = 0, message = e.localizedMessage))
            }
        }
    }
}
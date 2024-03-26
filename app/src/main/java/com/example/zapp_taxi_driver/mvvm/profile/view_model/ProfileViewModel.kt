package com.example.zapp_taxi_driver.mvvm.profile.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zapp_taxi_driver.data_source.api_manager.ApiRepository
import com.example.zapp_taxi_driver.data_source.api_manager.WebServices
import com.example.zapp_taxi_driver.helper.Enums
import com.example.zapp_taxi_driver.helper.helper_model.UserProfileResponseModel
import com.example.zapp_taxi_driver.mvvm.home.model.UserProfileRequestModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.io.Serializable

data class ProfileObj(
    var strName: String = "",
    var strEmail: String = "",
    var strMobile: String = "",
    var strOwnerName: String = "",
    var strOwnerEmail: String = "",
    var strOwnerMobile: String = "",
    var strLanguage: String = "",
    var strPassword: String = "",
    var strRegistrationNo: String = "",
    var strManufacturer: String = "",
    var strModel: String = "",
    var strSeating: String = "",
    var strVehicleType: String = "",
    var strFuelType: String = "",
    var strEngineCapacity: String = "",
    var strProfile: String = "",
    var strLicense: String = "",
    var strRegistrationImage: String = "",
    var strInsurance: String = "",
    var strPermit: String = "",
):Serializable

class ProfileViewModel : ViewModel() {

    val profileObj = ProfileObj()

    val mutUserProfileResponse : MutableLiveData<UserProfileResponseModel?> = MutableLiveData()
    fun userProfileApi(model : UserProfileRequestModel){
        viewModelScope.launch(Dispatchers.IO + CoroutineExceptionHandler { _, throwable ->
            throwable.printStackTrace()
            mutUserProfileResponse.postValue(UserProfileResponseModel(code = 0 , message = throwable.localizedMessage))
        }){
            try {
                ApiRepository
                    .apiPost<UserProfileRequestModel, UserProfileResponseModel>(WebServices.getUserProfileUrl() , model)
                    .collectLatest {
                        mutUserProfileResponse.postValue(it)
                    }
            }catch (e:Exception){
                mutUserProfileResponse.postValue(UserProfileResponseModel(code = 0 , message = e.localizedMessage))
            }
        }
    }
}
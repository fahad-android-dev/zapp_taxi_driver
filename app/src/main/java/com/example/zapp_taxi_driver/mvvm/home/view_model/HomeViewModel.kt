package com.example.zapp_taxi_driver.mvvm.home.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zapp_taxi_driver.data_source.api_manager.ApiRepository
import com.example.zapp_taxi_driver.data_source.api_manager.WebServices
import com.example.zapp_taxi_driver.helper.helper_model.LocationServiceRequestModel
import com.example.zapp_taxi_driver.helper.helper_model.LocationServiceResponseModel
import com.example.zapp_taxi_driver.helper.helper_model.UserProfileResponseModel
import com.example.zapp_taxi_driver.helper.helper_model.UserResponseModel
import com.example.zapp_taxi_driver.mvvm.home.model.UserProfileRequestModel
import com.example.zapp_taxi_driver.mvvm.login.model.LoginRequestModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

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

   /* fun updateLocationApi(model: LocationServiceRequestModel, onCompletion: ((response: LocationServiceResponseModel) -> Unit)? = null) {
        viewModelScope.launch(Dispatchers.IO + CoroutineExceptionHandler { _, throwable ->
            throwable.printStackTrace()
            viewModelScope.launch(Dispatchers.Main) {
                onCompletion?.invoke(LocationServiceResponseModel(code = 0, message = throwable.localizedMessage ?: ""))
            }
        }) {
            try {
                ApiRepository.apiPost<LocationServiceRequestModel, LocationServiceResponseModel>(WebServices.getUpdateLocationUrl(), model).collectLatest {
                    viewModelScope.launch(Dispatchers.Main) {
                        onCompletion?.invoke(it)
                    }
                }
            } catch (e: Exception) {
                viewModelScope.launch(Dispatchers.Main) {
                    onCompletion?.invoke(LocationServiceResponseModel(code = 0, message = e.localizedMessage ?: ""))
                }
            }
        }
    }*/
}
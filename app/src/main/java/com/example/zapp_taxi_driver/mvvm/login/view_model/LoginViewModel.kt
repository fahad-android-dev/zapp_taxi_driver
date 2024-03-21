package com.example.zapp_taxi_driver.mvvm.login.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zapp_taxi_driver.data_source.api_manager.ApiRepository
import com.example.zapp_taxi_driver.data_source.api_manager.WebServices
import com.example.zapp_taxi_driver.helper.Validation
import com.example.zapp_taxi_driver.mvvm.login.model.LoginRequestModel
import com.example.zapp_taxi_driver.helper.helper_model.UserResponseModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

data class LoginObj(
    var strPhone: String = "",
    var strPassword: String = ""
)
class LoginViewModel : ViewModel() {
    var objLogin = LoginObj()
    val mutLoginResponse : MutableLiveData<UserResponseModel?> = MutableLiveData()
    fun loginUser(model : LoginRequestModel){
        viewModelScope.launch(Dispatchers.IO + CoroutineExceptionHandler { _, throwable ->
            throwable.printStackTrace()
            mutLoginResponse.postValue(UserResponseModel(code = "0" , message = throwable.localizedMessage))
        }){
            try {
                ApiRepository
                    .apiPost<LoginRequestModel,UserResponseModel>(WebServices.getLoginUrl() , model)
                    .collectLatest {
                        mutLoginResponse.postValue(it)
                }
            }catch (e:Exception){
                mutLoginResponse.postValue(UserResponseModel(code = "0" , message = e.localizedMessage))
            }
        }
    }

    fun checkValidation(block: (value: Int) -> Unit) {
        when {
            objLogin.strPhone.isEmpty() -> block(1)
            objLogin.strPhone.length < 10 -> block(2)
            objLogin.strPassword.isEmpty() -> block(3)
            objLogin.strPassword.length < 4 -> block(4)
            else -> block(0)
        }
    }

}
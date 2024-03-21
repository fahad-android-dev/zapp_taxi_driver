package com.example.zapp_taxi_driver.mvvm.register.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zapp_taxi_driver.data_source.api_manager.ApiRepository
import com.example.zapp_taxi_driver.data_source.api_manager.WebServices
import com.example.zapp_taxi_driver.helper.Enums
import com.example.zapp_taxi_driver.helper.helper_model.AddImageModel
import com.example.zapp_taxi_driver.helper.helper_model.UserResponseModel
import com.example.zapp_taxi_driver.mvvm.register.model.RegisterRequestModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

data class RegisterObj(
    var strName: String = "",
    var strEmail: String = "",
    var strMobile: String = "",
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
    var imageType: Enums.RegisterImageType = Enums.RegisterImageType.PROFILE
)
class RegisterViewModel : ViewModel() {

    var registerObj = RegisterObj()

    var arrListImages = arrayListOf<AddImageModel>()

    fun checkValidation(block: (value: Int) -> Unit) {
        when {
            registerObj.strName.isEmpty() -> block(1)
            registerObj.strEmail.isEmpty() -> block(2)
            registerObj.strMobile.isEmpty() -> block(3)
            registerObj.strLanguage.isEmpty() -> block(4)
            registerObj.strPassword.isEmpty() -> block(5)
            registerObj.strProfile.isEmpty() -> block(6)
            registerObj.strLicense.isEmpty() -> block(7)
            registerObj.strRegistrationImage.isEmpty() -> block(8)
            registerObj.strInsurance.isEmpty() -> block(9)
            registerObj.strPermit.isEmpty() -> block(10)
            registerObj.strRegistrationNo.isEmpty() -> block(11)
            registerObj.strManufacturer.isEmpty() -> block(12)
            registerObj.strModel.isEmpty() -> block(13)
            registerObj.strSeating.isEmpty() -> block(14)
            registerObj.strVehicleType.isEmpty() -> block(15)
            registerObj.strFuelType.isEmpty() -> block(16)
            registerObj.strEngineCapacity.isEmpty() -> block(17)
            else -> block(0)
        }
    }


    val mutRegisterResponse : MutableLiveData<UserResponseModel?> = MutableLiveData()
    fun registerApi(model : RegisterRequestModel){
        viewModelScope.launch(Dispatchers.IO + CoroutineExceptionHandler { _, throwable ->
            throwable.printStackTrace()
            mutRegisterResponse.postValue(UserResponseModel(code = "0" , message = throwable.localizedMessage))
        }){
            try {
                ApiRepository
                    .apiPost<RegisterRequestModel, UserResponseModel>(WebServices.getRegisterUrl() , model)
                    .collectLatest {
                        mutRegisterResponse.postValue(it)
                    }
            }catch (e:Exception){
                mutRegisterResponse.postValue(UserResponseModel(code = "0" , message = e.localizedMessage))
            }
        }
    }
}
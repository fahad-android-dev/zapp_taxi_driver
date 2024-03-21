package com.example.zapp_taxi_driver.helper

import android.content.Context
import com.example.zapp_taxi_driver.helper.Extensions.asString
import com.example.zapp_taxi_driver.helper.helper_model.AppConfigModel
import com.example.zapp_taxi_driver.helper.helper_model.DeepLinkModel
import com.example.zapp_taxi_driver.helper.helper_model.Device
import com.example.zapp_taxi_driver.helper.helper_model.StoreDataModel
import com.example.zapp_taxi_driver.helper.helper_model.UserRememberDataModel
import com.example.zapp_taxi_driver.helper.helper_model.UserResponseModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking

object PrefUtils {

    /**  -----------------------      USER DATA ---------------------------------- */
    fun Context.setUserDataResponse(result: UserResponseModel?) {
        val dt = DataStoreManager(this)
        runBlocking { dt.saveUserData(result) }
    }

    fun Context.getUserDataResponse(): UserResponseModel? {
        val dt = DataStoreManager(this)
        return runBlocking {
            dt.getUserData().first()
        }
    }

    /**  -----------------------      USER REMEMBER DATA ---------------------------------- */

    fun Context.setUserRememberData(result: UserRememberDataModel) {
        val dt = DataStoreManager(this)
        runBlocking { dt.saveUserRememberData(result) }
    }

    fun Context.getUserRememberData(): UserRememberDataModel {
        val dt = DataStoreManager(this)
        return runBlocking {
            dt.getUserRememberData().firstOrNull()?.data ?: UserRememberDataModel()
        }
    }

    fun Context.saveDeepLink(result: DeepLinkModel?) {
        val dt = DataStoreManager(this)
        runBlocking { dt.saveDeeplinkModel(result) }
    }


    fun Context.getDeepLink(): DeepLinkModel? {
        val dt = DataStoreManager(this)
        return runBlocking {
            dt.getDeeplinkModel().firstOrNull()
        }
    }

    fun Context.getUserId(): String {
        return getUserDataResponse()?.user_id ?: ""
    }


    fun Context.isUserLoggedIn(): Boolean {
        return this.getUserDataResponse()?.user_id != null
    }

    fun Context.getDeviceModel(): Device {
        return Device(
            device_model = Constants.DEVICE_MODEL,
            device_token = Constants.DEVICE_TOKEN,
            device_type = Constants.DEVICE_TYPE,
            //app_version = Constants.APP_VERSION,
            os_version = Constants.OS_VERSION
        )
    }

    /**  -----------------------  ------------------------------------  ---------------------------------- */

    /**  -----------------------      STORE         ---------------------------------- */
    fun Context.setPrefStoreData(result: StoreDataModel) {
        val dt = DataStoreManager(this)
        runBlocking { dt.saveStore(result) }
    }

    fun Context.getPrefStoreData(): StoreDataModel? {
        val dt = DataStoreManager(this)
        return runBlocking { dt.getStore().firstOrNull() }
    }

    fun Context.getStore(): String {
        return getPrefStoreData()?.store_code ?: ""
    }

    fun Context.getStoreFlag(): String {
        return getPrefStoreData()?.flag ?: ""
    }

    fun Context.getPrefCurrencyEN(): String {
        return getPrefStoreData()?.currency_code_en ?: ""
    }

    fun Context.getPrefCurrencyAR(): String {
        return getPrefStoreData()?.currency_code_ar ?: ""
    }

    /**  -----------------------  ------------------------------------  ---------------------------------- */

    /**  -----------------------      APP CONFIG         ---------------------------------- */

    fun Context.setAppConfig(result: AppConfigModel) {
        val dt = DataStoreManager(this)
        runBlocking { dt.saveAppConfig(result) }
    }

    fun Context.getAppConfig(): AppConfigModel? {
        val dt = DataStoreManager(this)
        return runBlocking { dt.getAppConfig().firstOrNull() }
    }

    fun Context.isEnglishLanguage(): Boolean {
        return getAppConfig()?.lang == "en"
    }

    fun Context.updateBadgeCount(count:String) {
        val model = getAppConfig()
        model?.cartBadgeCount = count
        setAppConfig(AppConfigModel(lang = model?.lang ?: "", cartBadgeCount = model?.cartBadgeCount ?: ""))
    }

    /**  -----------------------  ------------------------------------  ---------------------------------- */


}

package com.example.zapp_taxi_driver.helper

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.LocaleList
import com.example.zapp_taxi_driver.helper.PrefUtils.getAppConfig
import com.example.zapp_taxi_driver.helper.PrefUtils.getPrefStoreData
import com.example.zapp_taxi_driver.helper.PrefUtils.setAppConfig
import com.example.zapp_taxi_driver.helper.PrefUtils.setPrefStoreData
import com.example.zapp_taxi_driver.helper.helper_model.AppConfigModel
import com.example.zapp_taxi_driver.helper.helper_model.StoreDataModel
import java.util.*

class AppController : Application() {
    companion object {
        lateinit var instance: AppController
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "location",
                "Location",
                NotificationManager.IMPORTANCE_LOW
            )
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

        //FacebookSdk.sdkInitialize(this)
        //Pushwoosh Token Generate
        //Pushwoosh.getInstance().registerForPushNotifications()
        //Constants.DEVICE_TOKEN = Pushwoosh.getInstance().pushToken ?: ""

        /** after adding branch key in strings uncomment this */
        /*Branch.getAutoInstance(this)// Branch object initialization
        Branch.enableTestMode() // Branch logging for debugging
        Branch.disableDebugMode()
        Branch.disableLogging()*/

        println("Pushwoosh Token :::: ${Constants.DEVICE_TOKEN}")

        if (getPrefStoreData() == null) {
            setPrefStoreData(
                StoreDataModel(
                    currency_code_ar = "د.ك.",
                    currency_code_en = "KD",
                    dial_code = "965",
                    flag = "https://magento.curlnationkw.com/pub/media/stores/kuwait.png",
                    is_default = "1",
                    iso2_code = "KWT",
                    name = "Kuwait",
                    name_ar = "الكويت",
                    store_code = "KWD",
                    isSelected = true
                )
            )
        }

        //it means -if no language set then it will check device language and set device language as default
        if (getAppConfig()?.lang?.isEmpty() == true) {
            if (Locale.getDefault().displayLanguage.equals("English", true)) {
                LocaleHelper.setLocale(this, "en")
            } else {
                LocaleHelper.setLocale(this, "ar")
            }
        } else {
            LocaleHelper.setLocale(this, getAppConfig()?.lang ?: "en")
        }
        initInstaBug()

    }

    fun setLocale() {
        val locale: Locale = resources.configuration.locale

        val res = resources
        val dm = res.displayMetrics
        val conf = res.configuration

        val localeList = LocaleList(locale)
        LocaleList.setDefault(localeList)
        conf.setLocales(localeList)

        conf.setLayoutDirection(locale)
        res.updateConfiguration(conf, dm)
    }

    fun arabicLanguage() {
        val model = getAppConfig()
        setAppConfig(AppConfigModel(lang = "ar", cartBadgeCount = model?.cartBadgeCount ?: ""))
        setLocale()
    }

    fun englishLanguage() {
        val model = getAppConfig()
        setAppConfig(AppConfigModel(lang = "en", cartBadgeCount = model?.cartBadgeCount ?: ""))
        setLocale()
    }

    private fun initInstaBug() {
        if (Global.isTestModeEnabled) {
            /*Instabug.Builder(this, Global.INSTABUG_KEY_DEBUG)
                .setInvocationEvents(InstabugInvocationEvent.FLOATING_BUTTON, InstabugInvocationEvent.SHAKE)
                .build()*/
        }
    }

}
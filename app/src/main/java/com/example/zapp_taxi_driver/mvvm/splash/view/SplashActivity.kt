package com.example.zapp_taxi_driver.mvvm.splash.view

import AppNavigation.navigateToHome
import AppNavigation.navigateToIntro
import android.annotation.SuppressLint
import android.os.Bundle

import androidx.databinding.DataBindingUtil
import com.example.zapp_taxi_driver.R
import com.example.zapp_taxi_driver.databinding.ActivitySplashBinding
import com.example.zapp_taxi_driver.helper.BaseActivity
import com.example.zapp_taxi_driver.helper.Extensions.handler
import com.example.zapp_taxi_driver.helper.PrefUtils.isUserLoggedIn

@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseActivity() {

    override fun onStart() {
        super.onStart()
        // Branch init
        /** after adding branch key in strings uncomment this*/
        /*Branch.sessionBuilder(this)
            .withData(this.intent.data)
            .withCallback { referringParams, error ->
                initSaveDeepLink(referringParams, error)
            }.init()*/
    }

    private lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)
        initializeFields()
    }

    private fun initializeFields() {
        handler(3000) {
            if (isUserLoggedIn()) {
                navigateToHome { finish() }
            } else {
                navigateToIntro()
                finish()
            }
        }
    }
}

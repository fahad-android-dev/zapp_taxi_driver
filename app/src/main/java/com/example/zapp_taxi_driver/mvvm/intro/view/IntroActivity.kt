package com.example.zapp_taxi_driver.mvvm.intro.view

import AppNavigation.navigateToLogin
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.zapp_taxi_driver.R
import com.example.zapp_taxi_driver.databinding.ActivityIntroBinding
import com.example.zapp_taxi_driver.helper.AppController

class IntroActivity : AppCompatActivity() {
    private lateinit var binding: ActivityIntroBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_intro)

        onClickListeners()
    }

    private fun onClickListeners(){
        binding.btnEnglish.setOnClickListener {
            AppController.instance.englishLanguage()
            navigateToLogin()
        }
        binding.btnArabic.setOnClickListener {
            AppController.instance.arabicLanguage()
            navigateToLogin()
        }
    }
}
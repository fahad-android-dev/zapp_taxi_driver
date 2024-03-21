package com.example.zapp_taxi_driver.mvvm.login.view

import AppNavigation.navigateToHome
import AppNavigation.navigateToRegister
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.zapp_taxi_driver.mvvm.login.model.LoginRequestModel
import com.example.zapp_taxi_driver.R
import com.example.zapp_taxi_driver.databinding.ActivityLoginBinding
import com.example.zapp_taxi_driver.helper.*
import com.example.zapp_taxi_driver.helper.Extensions.isInternetEnabled
import com.example.zapp_taxi_driver.helper.Global.showSnackBar
import com.example.zapp_taxi_driver.helper.PrefUtils.getUserRememberData
import com.example.zapp_taxi_driver.helper.PrefUtils.setUserDataResponse
import com.example.zapp_taxi_driver.helper.PrefUtils.setUserRememberData
import com.example.zapp_taxi_driver.helper.helper_model.UserRememberDataModel

import com.example.zapp_taxi_driver.mvvm.login.view_model.LoginViewModel
import kotlinx.coroutines.launch

class LoginActivity : SocialRegisterActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.viewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        binding.viewModel = viewModel

        initializeFields()
        onClickListeners()
        initObserver()
    }

    private fun initializeFields() {
    }

    private fun initObserver() {
        viewModel.mutLoginResponse.observe(this) {
            hideProgressDialog()
            lifecycleScope.launch {
                if (it != null) {
                    if (it.code == "404") {
                        setUserDataResponse(it)
                        navigateToHome(it.message ?: "") { finish() }
                    } else {
                        binding.root.showSnackBar(it.message ?: "")
                    }
                } else {
                    binding.root.showSnackBar(getString(R.string.error_message))
                }
            }
        }

    }

    private fun onClickListeners() {
        binding.btnLogin.setOnClickListener {
            viewModel.checkValidation {
                when (it) {
                    1 -> binding.root.showSnackBar(getString(R.string.error_please_enter_phone_number))
                    2 -> binding.root.showSnackBar(getString(R.string.error_please_enter_valid_phone_number))
                    3 -> binding.root.showSnackBar(getString(R.string.error_enter_password))
                    4 -> binding.root.showSnackBar(getString(R.string.error_password_length))
                    0 -> callLoginApi()
                }
            }
        }
        binding.btnRegister.setOnClickListener {
            navigateToRegister()
        }
        binding.ivPasswordEye.setOnClickListener {
            if (binding.edtPassword.transformationMethod == null) {
                binding.edtPassword.transformationMethod = PasswordTransformationMethod()
                binding.edtPassword.setSelection(binding.edtPassword.text?.length ?: 0)
                binding.ivPasswordEye.setImageDrawable(ContextCompat.getDrawable(this@LoginActivity, R.drawable.ic_eye))
            } else {
                binding.edtPassword.transformationMethod = null
                binding.edtPassword.setSelection(binding.edtPassword.text?.length ?: 0)
                binding.ivPasswordEye.setImageDrawable(ContextCompat.getDrawable(this@LoginActivity, R.drawable.ic_eye_closed))
            }
        }
    }

    private fun callLoginApi() {
        isInternetEnabled {
            showProgressDialog()
            viewModel.loginUser(
                model = LoginRequestModel(
                    mobile = viewModel.objLogin.strPhone,
                    password = viewModel.objLogin.strPassword,
                )
            )
        }
    }
}
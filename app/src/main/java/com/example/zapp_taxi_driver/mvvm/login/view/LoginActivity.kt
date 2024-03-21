package com.example.zapp_taxi_driver.mvvm.login.view

import AppNavigation.navigateToHome
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
        getUserRememberData().let {
            if (it.isRemember == true) {
                viewModel.objLogin.strEmail = it.emailOrPhone ?: ""
                viewModel.objLogin.strPassword = it.password ?: ""
                binding.ivRememberMeCheck.isSelected = true
            }
        }
    }

    private fun initObserver() {
        viewModel.mutLoginResponse.observe(this) {
            hideProgressDialog()
            lifecycleScope.launch {
                if (it != null) {
                    if (it.status == 200) {
                        it.message = getString(R.string.user_login_successfully)
                        setUserDataResponse(it)
                        setUserRememberData(
                            UserRememberDataModel(
                                emailOrPhone  = viewModel.objLogin.strEmail,
                                password =  viewModel.objLogin.strPassword,
                                isRemember  = binding.ivRememberMeCheck.isSelected
                            )
                        )
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
                    1 -> binding.root.showSnackBar(getString(R.string.error_enter_email))
                    2 -> binding.root.showSnackBar(getString(R.string.error_invalid_email))
                    3 -> binding.root.showSnackBar(getString(R.string.error_enter_password))
                    4 -> binding.root.showSnackBar(getString(R.string.error_password_length))
                    0 -> isInternetEnabled { navigateToHome { finish() } }
                }
            }
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

        binding.linRememberMe.setOnClickListener {
            binding.ivRememberMeCheck.isSelected = binding.ivRememberMeCheck.isSelected != true
        }

        binding.txtCreateNew.setOnClickListener {

        }

        binding.ivGoogle.setOnClickListener {
            /*onGoogleLogin(OnGoogleSuccess = {
                *//*viewModel.strSocialFirstName = it.strSocialFirstName
                viewModel.strSocialLastName = it.strSocialLastName
                viewModel.strSocialEmail = it.strSocialEmail
                viewModel.strSocialImage = it.strSocialImage

                showProgressDialog("F")
                isInternetEnabled {
                    viewModel.socialRegisterUser("G")
                }*//*
            } , OnError = {
                binding.root.showSnackBar(it)
            } )*/
        }

        binding.ivFacebook.setOnClickListener {
            /*onFacebookLogin(OnFacebookSuccess = {
               *//* viewModel.strSocialFirstName = it.strSocialFirstName
                viewModel.strSocialLastName = it.strSocialLastName
                viewModel.strSocialEmail = it.strSocialEmail
                viewModel.strSocialImage = it.strSocialImage

                showProgressDialog("F")
                isInternetEnabled { viewModel.socialRegisterUser("F") }*//*
            } , OnError = {
                binding.root.showSnackBar(it)
            } )*/
        }
    }

    private fun callLoginApi() {
        showProgressDialog()
        viewModel.loginUser(
            model = LoginRequestModel(
                email = viewModel.objLogin.strEmail,
                password = viewModel.objLogin.strPassword,
                app_version = Constants.APP_VERSION,
                device_model = Constants.DEVICE_MODEL,
                device_token = Constants.DEVICE_TOKEN,
                device_type = Constants.DEVICE_TYPE,
                os_version = Constants.OS_VERSION
            )
        )
    }
}
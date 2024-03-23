package com.example.zapp_taxi_driver.mvvm.register.view

import AppNavigation.navigateToHome
import android.app.Activity
import android.net.Uri
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.zapp_taxi_driver.R
import com.example.zapp_taxi_driver.databinding.ActivityRegisterBinding
import com.example.zapp_taxi_driver.helper.BaseActivity
import com.example.zapp_taxi_driver.helper.Dialogs
import com.example.zapp_taxi_driver.helper.Enums
import com.example.zapp_taxi_driver.helper.Extensions.getCameraIntent
import com.example.zapp_taxi_driver.helper.Extensions.getGalleryIntent
import com.example.zapp_taxi_driver.helper.Extensions.isInternetEnabled
import com.example.zapp_taxi_driver.helper.Extensions.receiveDataFromCamera
import com.example.zapp_taxi_driver.helper.Extensions.receiveDataFromPickMediaGallery
import com.example.zapp_taxi_driver.helper.Global.showSnackBar
import com.example.zapp_taxi_driver.helper.PrefUtils.setUserDataResponse
import com.example.zapp_taxi_driver.helper.helper_model.AddImageModel
import com.example.zapp_taxi_driver.helper.interfaces.CommonInterfaceClickEvent
import com.example.zapp_taxi_driver.helper.interfaces.ImagePickerDialogInterface
import com.example.zapp_taxi_driver.mvvm.common.model.CommonSelectionModel
import com.example.zapp_taxi_driver.mvvm.common.view.CommonSelectionBottomSheetFragment
import com.example.zapp_taxi_driver.mvvm.common.view.CommonSelectionObj
import com.example.zapp_taxi_driver.mvvm.login.model.LoginRequestModel
import com.example.zapp_taxi_driver.mvvm.register.model.RegisterRequestModel
import com.example.zapp_taxi_driver.mvvm.register.view_model.RegisterViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.coroutines.launch

class RegisterActivity : BaseActivity() {
    private lateinit var binding : ActivityRegisterBinding
    private lateinit var viewModel: RegisterViewModel
    private var image :String ? = ""
    private var photoUri : Uri? = null
    private var isFromFuelType = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.viewModel = ViewModelProvider(this)[RegisterViewModel::class.java]
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register)
        binding.viewModel = viewModel

        initializeFields()
        onClickListeners()
        initObserver()
    }

    private fun initializeFields() {
        viewModel.registerObj.strLanguage = "English"
        binding.layoutToolbar.root.background = ContextCompat.getDrawable(this@RegisterActivity,R.color.color_white)
    }


    private fun initObserver() {
             viewModel.mutRegisterResponse.observe(this) {
                 hideProgressDialog()
                 lifecycleScope.launch {
                     if (it != null) {
                         if (it.code == "200") {
                             setUserDataResponse(it)
                             navigateToHome(it.message ?: "") { finish() }
                         } else {
                             binding.root.showSnackBar(getString(R.string.error_message))
                         }
                     } else {
                         binding.root.showSnackBar(getString(R.string.error_message))
                     }
                 }
             }

        viewModel.mutVehicleTypeResponse.observe(this) {
            hideProgressDialog()
            lifecycleScope.launch {
                if (it != null) {
                    if (it.code == "200") {
                        viewModel.vehicleTypeResponse = it
                        viewModel.commonSelectionModel.clear()
                        viewModel.vehicleTypeResponse?.data?.forEach {
                            viewModel.commonSelectionModel.add(
                                CommonSelectionModel(
                                    name = it?.name ?: "",
                                    fuel_type = it?.fuel_type ?: "",
                                    isSelected = false,
                                    id = it?.id ?: ""
                                )
                            )
                        }
                        showSelectionDialog()

                    } else {
                        binding.root.showSnackBar(getString(R.string.error_message))
                    }
                } else {
                    binding.root.showSnackBar(getString(R.string.error_message))
                }
            }
        }

         }

    private fun onClickListeners() {
        binding.btnRegister.setOnClickListener {
            viewModel.checkValidation {
                when (it) {
                    1 -> binding.root.showSnackBar(getString(R.string.please_enter_your_name))
                    2 -> binding.root.showSnackBar(getString(R.string.please_enter_your_email))
                    3 -> binding.root.showSnackBar(getString(R.string.please_enter_your_mobile_number))
                    4 -> binding.root.showSnackBar(getString(R.string.please_select_language))
                    5 -> binding.root.showSnackBar(getString(R.string.please_enter_password))
                    6 -> binding.root.showSnackBar(getString(R.string.please_select_profile_photo))
                    7 -> binding.root.showSnackBar(getString(R.string.please_upload_your_drivers_license))
                    8 -> binding.root.showSnackBar(getString(R.string.please_upload_your_rc))
                    9 -> binding.root.showSnackBar(getString(R.string.please_upload_your_vehicle_insurance))
                    10 -> binding.root.showSnackBar(getString(R.string.please_upload_your_vehicle_permit))
                    11 -> binding.root.showSnackBar(getString(R.string.please_enter_registration_number))
                    12 -> binding.root.showSnackBar(getString(R.string.please_enter_your_cars_manufacturer))
                    13 -> binding.root.showSnackBar(getString(R.string.please_enter_model_number))
                    14 -> binding.root.showSnackBar(getString(R.string.please_enter_your_cars_seating_capacity))
                    15 -> binding.root.showSnackBar(getString(R.string.please_enter_your_vehicle_type))
                    16 -> binding.root.showSnackBar(getString(R.string.please_enter_your_fuel_type))
                    17 -> binding.root.showSnackBar(getString(R.string.please_enter_your_vehicle_engine_capacity))
                    0 -> callRegisterApi()
                }
            }
        }

        binding.layoutToolbar.linBackArrow.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        binding.btnProfile.setOnClickListener {
            viewModel.registerObj.imageType = Enums.RegisterImageType.PROFILE
            Dialogs.showImagePickerDialog(this@RegisterActivity, imagePickerDialogInterFace, isFileEnabled = false)
        }

        binding.btnDriverLicense.setOnClickListener {
            viewModel.registerObj.imageType = Enums.RegisterImageType.LICENSE
            Dialogs.showImagePickerDialog(this@RegisterActivity, imagePickerDialogInterFace, isFileEnabled = false)
        }
        binding.btnRegistration.setOnClickListener {
            viewModel.registerObj.imageType = Enums.RegisterImageType.REGISTRATION
            Dialogs.showImagePickerDialog(this@RegisterActivity, imagePickerDialogInterFace, isFileEnabled = false)
        }
        binding.btnVehicleInsurance.setOnClickListener {
            viewModel.registerObj.imageType = Enums.RegisterImageType.INSURANCE
            Dialogs.showImagePickerDialog(this@RegisterActivity, imagePickerDialogInterFace, isFileEnabled = false)
        }
        binding.btnVehiclePermit.setOnClickListener {
            viewModel.registerObj.imageType = Enums.RegisterImageType.PERMIT
            Dialogs.showImagePickerDialog(this@RegisterActivity, imagePickerDialogInterFace, isFileEnabled = false)
        }

        binding.ivPasswordEye.setOnClickListener {
            if (binding.edtPassword.transformationMethod == null) {
                binding.edtPassword.transformationMethod = PasswordTransformationMethod()
                binding.edtPassword.setSelection(binding.edtPassword.text?.length ?: 0)
                binding.ivPasswordEye.setImageDrawable(ContextCompat.getDrawable(this@RegisterActivity, R.drawable.ic_eye))
            } else {
                binding.edtPassword.transformationMethod = null
                binding.edtPassword.setSelection(binding.edtPassword.text?.length ?: 0)
                binding.ivPasswordEye.setImageDrawable(ContextCompat.getDrawable(this@RegisterActivity, R.drawable.ic_eye_closed))
            }
        }

        binding.edtVehicleType.setOnClickListener{
            isFromFuelType = false
            callVehicleTypeApi()
        }
        binding.edtFuelType.setOnClickListener {
            isFromFuelType = true
            callFuelTypeApi()
        }
    }


    private var imagePickerDialogInterFace = object : ImagePickerDialogInterface {
        override fun onClickCamera() {
            ifCameraIsEnabled {
                getCameraIntent { intent, filePath, fileUri ->
                    image = filePath
                    photoUri = fileUri
                    launchCameraIntent.launch(intent)
                }
            }
        }

        override fun onClickGallery() {
            ifGalleryIsEnabled {
                getGalleryIntent { intent ->
                    launchGalleryIntent.launch(intent)
                }
            }
        }
    }

    private var launchCameraIntent = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            try {
                receiveDataFromCamera(
                    image,
                    photoUri
                ) { model ->
                    viewModel.arrListImages.clear()
                    viewModel.arrListImages.add(
                        AddImageModel(
                            image = model.image,
                            title = model.title,
                            imgBase64 = model.imgBase64,
                            filePath = model.filePath,
                            imageID = "",
                            imageUrl = ""
                        )
                    )
                    updateImages()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private var launchGalleryIntent = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        if (uri != null){
            try {
                receiveDataFromPickMediaGallery(result = uri){ model ->
                    viewModel.arrListImages.clear()
                    viewModel.arrListImages.add(
                        AddImageModel(
                            image = model.image,
                            title = model.title,
                            imgBase64 = model.imgBase64,
                            filePath = model.filePath,
                            imageID = "",
                            imageUrl = ""
                        )
                    )
                    updateImages()
                }
            } catch (e: Exception) {
                println("here is exception ${e.message}")
            }
        }
    }

    private fun updateImages(){
        when (viewModel.registerObj.imageType) {
            Enums.RegisterImageType.PROFILE -> {
                viewModel.registerObj.strProfile = viewModel.arrListImages.firstOrNull()?.imgBase64 ?: ""
                binding.ivProfile.isVisible = true
                binding.ivProfile.setImageBitmap(viewModel.arrListImages.firstOrNull()?.image)
            }
            Enums.RegisterImageType.LICENSE -> {
                viewModel.registerObj.strLicense = viewModel.arrListImages.firstOrNull()?.imgBase64 ?: ""
                binding.ivDriverLicense.isVisible = true
                binding.ivDriverLicense.setImageBitmap(viewModel.arrListImages.firstOrNull()?.image)
            }
            Enums.RegisterImageType.REGISTRATION -> {
                viewModel.registerObj.strRegistrationImage = viewModel.arrListImages.firstOrNull()?.imgBase64 ?: ""
                binding.ivRegistration.isVisible = true
                binding.ivRegistration.setImageBitmap(viewModel.arrListImages.firstOrNull()?.image)
            }
            Enums.RegisterImageType.INSURANCE -> {
                viewModel.registerObj.strInsurance = viewModel.arrListImages.firstOrNull()?.imgBase64 ?: ""
                binding.ivVehicleInsurance.isVisible = true
                binding.ivVehicleInsurance.setImageBitmap(viewModel.arrListImages.firstOrNull()?.image)
            }
            Enums.RegisterImageType.PERMIT -> {
                viewModel.registerObj.strPermit = viewModel.arrListImages.firstOrNull()?.imgBase64 ?: ""
                binding.ivVehiclePermit.isVisible = true
                binding.ivVehiclePermit.setImageBitmap(viewModel.arrListImages.firstOrNull()?.image)
            }
        }

    }

    private fun callRegisterApi() {
        isInternetEnabled {
            showProgressDialog()
            viewModel.registerApi(
                model = RegisterRequestModel(
                    mobile = viewModel.registerObj.strMobile,
                    password = viewModel.registerObj.strPassword,
                    name = viewModel.registerObj.strName,
                    email_id = viewModel.registerObj.strEmail,
                    language = viewModel.registerObj.strLanguage,
                    vehicle_no = viewModel.registerObj.strRegistrationNo,
                    manufacturer = viewModel.registerObj.strManufacturer,
                    model = viewModel.registerObj.strModel,
                    seating_capacity = viewModel.registerObj.strSeating,
                    vehicle_type = viewModel.registerObj.strVehicleType,
                    fuel_type = viewModel.registerObj.strFuelType,
                    engine_capacity = viewModel.registerObj.strEngineCapacity,
                    profile = viewModel.registerObj.strProfile,
                    driving_licence = viewModel.registerObj.strLicense,
                    reg_cer = viewModel.registerObj.strRegistrationImage,
                    vehicle_insurance = viewModel.registerObj.strInsurance,
                    vehicle_permit = viewModel.registerObj.strPermit,

                )
            )
        }

    }


    private fun callVehicleTypeApi() {
        isInternetEnabled {
            showProgressDialog()
            viewModel.vehicleTypeApi(
                model = RegisterRequestModel()
            )
        }
    }
    private fun callFuelTypeApi() {
        isInternetEnabled {
            showProgressDialog()
            viewModel.fuelTypeApi(
                model = RegisterRequestModel()
            )
        }
    }

    private fun showSelectionDialog(){
        val bottomSheetFragment = CommonSelectionBottomSheetFragment()
        val bundle = Bundle()
        bundle.putSerializable("CommonSelectionObj", CommonSelectionObj(
            commonSelectionList = viewModel.commonSelectionModel,
            isFromFuelType = isFromFuelType,
            title = getString(R.string.label_vehicle_type
            ))
        )
        bottomSheetFragment.arguments = bundle
        val behavior =
            (bottomSheetFragment.view?.parent as? View)?.let { BottomSheetBehavior.from(it) }
        behavior?.state = BottomSheetBehavior.STATE_EXPANDED
        bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)
        bottomSheetFragment.commonClick = object : CommonInterfaceClickEvent {
            override fun onItemClick(type: String, position: Int) {
                if (isFromFuelType){
                    viewModel.registerObj.strFuelType = viewModel.vehicleTypeResponse?.data?.get(position)?.fuel_type.toString()
                    binding.edtFuelType.setText(viewModel.vehicleTypeResponse?.data?.get(position)?.fuel_type.toString())
                }else {
                    viewModel.registerObj.strVehicleType = viewModel.vehicleTypeResponse?.data?.get(position)?.name.toString()
                    binding.edtVehicleType.setText(viewModel.vehicleTypeResponse?.data?.get(position)?.name.toString())
                }

            }
        }
    }
}
package com.example.zapp_taxi_driver.mvvm.profile.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.zapp_taxi_driver.R
import com.example.zapp_taxi_driver.databinding.FragmentMyProfileBinding
import com.example.zapp_taxi_driver.helper.BaseActivity
import com.example.zapp_taxi_driver.helper.BaseFragment
import com.example.zapp_taxi_driver.helper.Extensions.isInternetEnabled
import com.example.zapp_taxi_driver.helper.Extensions.setTransparentToolbarOnScroll
import com.example.zapp_taxi_driver.helper.Global.loadImagesUsingCoil
import com.example.zapp_taxi_driver.helper.Global.showSnackBar
import com.example.zapp_taxi_driver.helper.PrefUtils.getUserDataResponse
import com.example.zapp_taxi_driver.helper.PrefUtils.getUserId
import com.example.zapp_taxi_driver.mvvm.driver_report.view_model.DriverReportViewModel
import com.example.zapp_taxi_driver.mvvm.home.model.UserProfileRequestModel
import com.example.zapp_taxi_driver.mvvm.profile.view_model.ProfileViewModel
import kotlinx.coroutines.launch


class MyProfileFragment : BaseFragment() {
    private lateinit var binding : FragmentMyProfileBinding
    private lateinit var viewModel : ProfileViewModel
    private lateinit var mActivity: BaseActivity
    val url = "http://68.183.92.60/Zap_taxi/assets/Upload/driver_detail/"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivity = activity as BaseActivity
        viewModel = ViewModelProvider(this)[ProfileViewModel::class.java]

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_my_profile,
            container,
            false
        )
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeFields()
        initializeToolbar()
        onClickListeners()
        initObserver()

    }

    private fun initializeToolbar(){
        binding.layoutToolbar.toolbar.background = ContextCompat.getDrawable(mActivity, R.color.color_transparent)
        binding.nestedScrollView.setTransparentToolbarOnScroll(
            activity = mActivity,
            view = binding.txtBasicDetails,
            binding = binding.layoutToolbar,
            value = viewModel.profileObj.strName
        )
    }

    private fun initializeFields(){
        callUserProfileApi()
    }

    private fun onClickListeners(){
        binding.layoutToolbar.linBackArrow.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun initObserver() {
        viewModel.mutUserProfileResponse.observe(viewLifecycleOwner) {
            mActivity.hideProgressDialog()
            lifecycleScope.launch {
                if (it != null) {
                    if (it.code == 200) {
                        viewModel.profileObj.strName = it.data?.firstname ?: ""
                        binding.edtName.setText(it.data?.firstname ?: "")
                        binding.edtEmail.setText(it.data?.email_id ?: "")
                        binding.edtMobile.setText(it.data?.mobile ?: "")
                        binding.edtOwnerName.setText(it.data?.owner_name ?: "")
                        binding.edtOwnerMobile.setText(it.data?.onw_mobile_no ?: "")
                        binding.edtLanguage.setText(it.data?.language ?: "")
                        binding.edtRegistrationNo.setText(it.data?.registration_no ?: "")
                        binding.edtManufacturer.setText(it.data?.make ?: "")
                        binding.edtModel.setText(it.data?.model ?: "")
                        binding.edtSeatingCapacity.setText(it.data?.seating ?: "")
                        binding.edtVehicleType.setText(it.data?.vehicle_type ?: "")
                        binding.edtFuelType.setText(it.data?.fuel_type ?: "")
                        binding.edtEngineCapacity.setText(it.data?.engine_capacity ?: "")
                        binding.edtFuelType.setText(it.data?.fuel_type ?: "")
                        binding.edtFuelType.setText(it.data?.fuel_type ?: "")
                        binding.edtLicense.setText(it.data?.driving_license_expiry ?: "")
                        binding.edtRc.setText(it.data?.rc_certificate_expiry ?: "")
                        binding.edtTaxiPermit.setText(it.data?.taxi_permit_expiry ?: "")
                        binding.edtTaxiInsurance.setText(it.data?.insurance_expiry_date ?: "")

                        binding.ivLicense.loadImagesUsingCoil(url+it.data?.driving_license)
                        binding.ivRC.loadImagesUsingCoil(url+it.data?.r_certificate)
                        binding.ivTaxiPermit.loadImagesUsingCoil(url+it.data?.taxi_permit)
                        binding.ivTaxiInsurance.loadImagesUsingCoil(url+it.data?.insurance_img)
                        binding.imgDetails.loadImagesUsingCoil(url+it.data?.profile_image)

                    } else {
                        binding.root.showSnackBar(getString(R.string.error_message))
                    }
                } else {
                    binding.root.showSnackBar(getString(R.string.error_message))
                }
            }
        }

    }


    private fun callUserProfileApi(){
        context?.isInternetEnabled{
            viewModel.userProfileApi(
                model = UserProfileRequestModel(
                    id = context?.getUserId(),
                    AuthToken = context?.getUserDataResponse()?.AuthToken
                )
            )
        }
    }
}
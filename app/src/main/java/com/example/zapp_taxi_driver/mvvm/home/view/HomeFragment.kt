package com.example.zapp_taxi_driver.mvvm.home.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.zapp_taxi_driver.R
import com.example.zapp_taxi_driver.databinding.FragmentHomeBinding
import com.example.zapp_taxi_driver.helper.BaseFragment
import com.example.zapp_taxi_driver.helper.Extensions
import com.example.zapp_taxi_driver.helper.Extensions.isInternetEnabled
import com.example.zapp_taxi_driver.helper.Global.showSnackBar
import com.example.zapp_taxi_driver.helper.PrefUtils.getUserDataResponse
import com.example.zapp_taxi_driver.helper.PrefUtils.getUserId
import com.example.zapp_taxi_driver.helper.location.LocationService
import com.example.zapp_taxi_driver.mvvm.home.model.AcceptRejectBookingRequestModel
import com.example.zapp_taxi_driver.mvvm.home.view_model.HomeViewModel
import kotlinx.coroutines.launch


class HomeFragment : BaseFragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var mActivity: HomeActivity
    private lateinit var viewModel : HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivity = activity as HomeActivity
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_home,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        initializeFields()
        onClickListeners()
        initObserver()
    }

    private fun onClickListeners(){
        binding.layoutHomeToolbar.imgDrawer.setOnClickListener {
            mActivity.openMainDrawers()
        }
        binding.switchButton.setOnCheckedChangeListener { view, isChecked ->
            if (isChecked){
                mActivity.ifLocationPermissionIsEnabled{
                    Intent(mActivity.applicationContext, LocationService::class.java).apply {
                        action = LocationService.ACTION_START
                        mActivity.startService(this)
                    }
                }
            }else {
                mActivity.ifLocationPermissionIsEnabled{
                    Intent(mActivity.applicationContext, LocationService::class.java).apply {
                        action = LocationService.ACTION_START
                        mActivity.stopService(this)
                    }
                }
            }
        }
        binding.layoutRideDetails.btnAcceptBooking.setOnClickListener {
            callAcceptBookingApi()
        }
        /*binding.layoutRideDetails.btnAcceptBooking.setOnClickListener {
            callRejectBookingApi()
        }*/
    }

    private fun initializeFields(){
        Extensions.handler(1000) {
            println("here is service running ${Extensions.isForegroundServiceRunning(mActivity)}")
            if (Extensions.isForegroundServiceRunning(mActivity)){
                binding.switchButton.isChecked = true
            }
        }

        LocationService.locationResponse.observe(viewLifecycleOwner) {
            binding.layoutRideDetails.root.isVisible = it.booking_local?.isEmpty() != true
        }

    }

     private fun initObserver() {
             viewModel.mutAcceptBookingResponse.observe(viewLifecycleOwner) {
                     mActivity.hideProgressDialog()
                     lifecycleScope.launch {
                         if (it != null) {
                             if (it.code == 200) {

                             } else {
                                 binding.root.showSnackBar(getString(R.string.error_message))
                             }
                         } else {
                             binding.root.showSnackBar(getString(R.string.error_message))
                         }
                     }
             }
         viewModel.mutCancelBookingResponse.observe(viewLifecycleOwner) {
             mActivity.hideProgressDialog()
             lifecycleScope.launch {
                 if (it != null) {
                     if (it.code == 200) {

                     } else {
                         binding.root.showSnackBar(getString(R.string.error_message))
                     }
                 } else {
                     binding.root.showSnackBar(getString(R.string.error_message))
                 }
             }
         }

         }

    private fun  callAcceptBookingApi(){
        context?.isInternetEnabled{
            mActivity.showProgressDialog()
            viewModel.acceptBookingApi(
                model = AcceptRejectBookingRequestModel(
                    id = context?.getUserId(),
                    AuthToken = context?.getUserDataResponse()?.AuthToken,
                    booking_id = null
                )
            )
        }
    }
    private fun  callCancelBookingApi(){
        context?.isInternetEnabled{
            mActivity.showProgressDialog()
            viewModel.cancelBookingApi(
                model = AcceptRejectBookingRequestModel(
                    id = context?.getUserId(),
                    AuthToken = context?.getUserDataResponse()?.AuthToken,
                    booking_id = null
                )
            )
        }
    }
}
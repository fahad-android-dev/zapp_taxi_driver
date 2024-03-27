package com.example.zapp_taxi_driver.mvvm.home.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import com.example.zapp_taxi_driver.R
import com.example.zapp_taxi_driver.databinding.FragmentHomeBinding
import com.example.zapp_taxi_driver.helper.BaseFragment
import com.example.zapp_taxi_driver.helper.Extensions
import com.example.zapp_taxi_driver.helper.helper_model.LocationServiceResponseModel
import com.example.zapp_taxi_driver.helper.interfaces.LocationServiceInterface
import com.example.zapp_taxi_driver.helper.location.LocationService


class HomeFragment : BaseFragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var mActivity: HomeActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivity = activity as HomeActivity
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
}
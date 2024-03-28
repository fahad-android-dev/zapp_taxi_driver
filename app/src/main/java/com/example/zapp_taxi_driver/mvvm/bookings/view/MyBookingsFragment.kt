package com.example.zapp_taxi_driver.mvvm.bookings.view

import AppNavigation.navigateToBookingDetails
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.zapp_taxi_driver.R
import com.example.zapp_taxi_driver.databinding.FragmentMyBookingsBinding
import com.example.zapp_taxi_driver.helper.BaseActivity
import com.example.zapp_taxi_driver.helper.BaseFragment
import com.example.zapp_taxi_driver.helper.Extensions.asInt
import com.example.zapp_taxi_driver.helper.Extensions.init
import com.example.zapp_taxi_driver.helper.Extensions.isInternetEnabled
import com.example.zapp_taxi_driver.helper.Extensions.setTransparentToolbarOnScroll
import com.example.zapp_taxi_driver.helper.Global.showSnackBar
import com.example.zapp_taxi_driver.helper.PrefUtils.getUserDataResponse
import com.example.zapp_taxi_driver.helper.PrefUtils.getUserId
import com.example.zapp_taxi_driver.helper.interfaces.CommonInterfaceClickEvent
import com.example.zapp_taxi_driver.mvvm.bookings.model.MyBookingsDataModel
import com.example.zapp_taxi_driver.mvvm.bookings.model.MyBookingsRequestModel
import com.example.zapp_taxi_driver.mvvm.bookings.view_model.MyBookingsViewModel
import com.example.zapp_taxi_driver.mvvm.driver_report.model.DriverDetailDataModel
import com.example.zapp_taxi_driver.mvvm.home.model.UserProfileRequestModel
import com.example.zapp_taxi_driver.mvvm.profile.view_model.ProfileViewModel
import kotlinx.coroutines.launch
import java.util.ArrayList


class MyBookingsFragment : BaseFragment() {
    private lateinit var binding: FragmentMyBookingsBinding
    private lateinit var viewModel: MyBookingsViewModel
    private lateinit var mActivity: BaseActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivity = activity as BaseActivity
        viewModel = ViewModelProvider(this)[MyBookingsViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_my_bookings,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeToolbar()
        initializeFields()
        onClickListeners()
        initObserver()

    }

    private fun initializeToolbar() {
        setUpToolbar(
            binding.layoutToolbar,
            navController = findNavController(),
            title = getString(R.string.label_my_bookings),
            toolbarClickListener = object : CommonInterfaceClickEvent {
                override fun onToolBarListener(type: String) {
                    when (type) {

                    }
                }
            }
        )
    }

    private fun initializeFields() {
        binding.rvMyBookings.adapter = viewModel.adapterMyBookings

        binding.layoutEmpty.init(
            title = getString(R.string.no_bookings),
            subTitle = getString(R.string.you_haven_t_completed_any_bookings_yet),
        )
        callMyBookingsApi(false)
    }

    private fun onClickListeners(){
        binding.swipeRefreshLayout.setOnRefreshListener {
            callMyBookingsApi(false)
        }
    }

    private fun initObserver() {
        viewModel.mutMyBookingsResponse.observe(viewLifecycleOwner) {
            mActivity.hideProgressDialog()
            binding.swipeRefreshLayout.isRefreshing = false
            lifecycleScope.launch {
                if (it != null) {
                    if (it.code == 200) {
                        setData(it.booking)
                    } else {
                        binding.root.showSnackBar(getString(R.string.error_message))
                    }
                } else {
                    binding.root.showSnackBar(getString(R.string.error_message))
                }
            }
        }

    }


    private fun setData(data: ArrayList<MyBookingsDataModel?>?) {
        viewModel.arrListMyBookings.clear()
        viewModel.arrListMyBookings.addAll(data ?: arrayListOf())
        binding.rvMyBookings.layoutAnimation = AnimationUtils.loadLayoutAnimation(mActivity, R.anim.layout_animation_fall_down)
        binding.rvMyBookings.isVisible = viewModel.arrListMyBookings.isNotEmpty()
        binding.layoutEmpty.root.isVisible = viewModel.arrListMyBookings.isEmpty()
        viewModel.updateMyBookingsAdapter()

        viewModel.adapterMyBookings.onClickEvent = object : CommonInterfaceClickEvent {
            override fun onItemClick(type: String, position: Int) {
                if (type == "bookingClicked"){
                    navigateToBookingDetails(
                        bookingDetailsObj = BookingDetailsObj(
                            model = viewModel.arrListMyBookings[position]
                        )
                    )
                }
            }
        }
    }

    private fun callMyBookingsApi(showLoader: Boolean = true){
        context?.isInternetEnabled{
            if (showLoader) mActivity.showProgressDialog()
            viewModel.myBookingsApi(
                model = MyBookingsRequestModel(
                    id = context?.getUserId().asInt(),
                    AuthToken = context?.getUserDataResponse()?.AuthToken
                )
            )
        }
    }

}
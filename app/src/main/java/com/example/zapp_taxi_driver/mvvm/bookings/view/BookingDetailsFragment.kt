package com.example.zapp_taxi_driver.mvvm.bookings.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.zapp_taxi_driver.R
import com.example.zapp_taxi_driver.databinding.FragmentBookingDetailsBinding
import com.example.zapp_taxi_driver.databinding.FragmentMyBookingsBinding
import com.example.zapp_taxi_driver.helper.BaseActivity
import com.example.zapp_taxi_driver.helper.BaseFragment
import com.example.zapp_taxi_driver.helper.Extensions.getSerializableViaArgument
import com.example.zapp_taxi_driver.helper.Extensions.init
import com.example.zapp_taxi_driver.helper.interfaces.CommonInterfaceClickEvent
import com.example.zapp_taxi_driver.mvvm.bookings.model.MyBookingsDataModel
import com.example.zapp_taxi_driver.mvvm.bookings.view_model.MyBookingsViewModel
import java.io.Serializable

data class BookingDetailsObj(
    val model : MyBookingsDataModel ?= null
):Serializable
class BookingDetailsFragment :  BaseFragment() {
    private lateinit var binding: FragmentBookingDetailsBinding
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
            R.layout.fragment_booking_details,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeToolbar()
        initializeFields()

    }

    private fun initializeToolbar() {
        setUpToolbar(
            binding.layoutToolbar,
            navController = findNavController(),
            title = getString(R.string.ride_details),
            toolbarClickListener = object : CommonInterfaceClickEvent {
                override fun onToolBarListener(type: String) {
                    when (type) {

                    }
                }
            }
        )
    }

    private fun initializeFields() {
        if (arguments?.containsKey("bookingDetailsObj") == true) {
            viewModel.bookingDetailsObj = arguments?.getSerializableViaArgument("bookingDetailsObj", BookingDetailsObj::class.java) ?: BookingDetailsObj()
        }
        setData()
    }

    private fun setData(){
        val data = viewModel.bookingDetailsObj.model
        binding.layoutLocation.tvSourceLocation.text = data?.source_address
        binding.layoutLocation.tvDestinationLocation.text = data?.destination_address
        binding.txtName.text = data?.name
        binding.txtDate.text = data?.updated_date
        binding.txtContact.text = data?.mobile_no
        binding.txtAmount.text = data?.amount
        binding.txtStatus.text = data?.status
        binding.txtPaymentStatus.text = data?.status
    }


}
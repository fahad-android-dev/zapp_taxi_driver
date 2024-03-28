package com.example.zapp_taxi_driver.mvvm.driver_report.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.zapp_taxi_driver.R
import com.example.zapp_taxi_driver.databinding.FragmentDriverReportBinding
import com.example.zapp_taxi_driver.helper.BaseActivity
import com.example.zapp_taxi_driver.helper.BaseFragment
import com.example.zapp_taxi_driver.helper.Extensions.isInternetEnabled
import com.example.zapp_taxi_driver.helper.Global.showSnackBar
import com.example.zapp_taxi_driver.helper.PrefUtils.getUserId
import com.example.zapp_taxi_driver.helper.interfaces.CommonInterfaceClickEvent
import com.example.zapp_taxi_driver.mvvm.common.view_model.CommonSelectionBottomSheetViewModel
import com.example.zapp_taxi_driver.mvvm.driver_report.model.DriverDetailDataModel
import com.example.zapp_taxi_driver.mvvm.driver_report.model.DriverReportRequestModel
import com.example.zapp_taxi_driver.mvvm.driver_report.view_model.DriverReportViewModel
import com.example.zapp_taxi_driver.mvvm.login.model.LoginRequestModel
import kotlinx.coroutines.launch
import java.util.ArrayList

class DriverReportFragment : BaseFragment() {
    private lateinit var binding : FragmentDriverReportBinding
    private lateinit var viewModel : DriverReportViewModel
    private lateinit var mActivity: BaseActivity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivity = activity as BaseActivity
        viewModel = ViewModelProvider(this)[DriverReportViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(requireContext()),
            R.layout.fragment_driver_report,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeToolbar()
        initializeFields()
        initObserver()
        onClickListeners()
    }

    private fun initializeFields(){
        binding.rvDriverReport.adapter = viewModel.adapterDriverReport
        callDriverReportApi()
    }

    private fun initializeToolbar() {
        setUpToolbar(
            binding.layoutToolbar,
            navController = findNavController(),
            title = getString(R.string.label_driver_report),
            toolbarClickListener = object : CommonInterfaceClickEvent {
                override fun onToolBarListener(type: String) {
                    when (type) {

                    }
                }
            }
        )
    }

    private fun onClickListeners(){

    }

     private fun initObserver() {
             viewModel.mutDriverReportResponse.observe(viewLifecycleOwner) {
                 mActivity.hideProgressDialog()
                 lifecycleScope.launch {
                     if (it != null) {
                         if (it.code == 200) {
                             setData(it.DriverDetails)
                         } else {
                             binding.root.showSnackBar(getString(R.string.error_message))
                         }
                     } else {
                         binding.root.showSnackBar(getString(R.string.error_message))
                     }
                 }
             }

         }

    private fun setData(data: ArrayList<DriverDetailDataModel?>?) {
        viewModel.arrListDriverReport.clear()
        viewModel.arrListDriverReport.addAll(data ?: arrayListOf())
        binding.rvDriverReport.layoutAnimation = AnimationUtils.loadLayoutAnimation(mActivity, R.anim.layout_animation_fall_down)
        viewModel.updateDriverReportAdapter()
    }


    private fun callDriverReportApi(){
        context?.isInternetEnabled{
            mActivity.showProgressDialog()
            viewModel.driverReportApi(
                DriverReportRequestModel(
                    id = context?.getUserId()
                )
            )
        }
    }

}
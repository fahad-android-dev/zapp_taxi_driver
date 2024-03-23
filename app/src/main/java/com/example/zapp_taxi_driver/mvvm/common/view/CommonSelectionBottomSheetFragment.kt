package com.example.zapp_taxi_driver.mvvm.common.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.zapp_taxi_driver.mvvm.common.model.CommonSelectionModel
import com.example.zapp_taxi_driver.mvvm.common.view_model.CommonSelectionBottomSheetViewModel
import com.example.zapp_taxi_driver.R
import com.example.zapp_taxi_driver.databinding.FragmentCommonSelectionBottomSheetBinding
import com.example.zapp_taxi_driver.helper.BaseActivity
import com.example.zapp_taxi_driver.helper.Extensions.getSerializableViaArgument
import com.example.zapp_taxi_driver.helper.interfaces.CommonInterfaceClickEvent
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.serialization.Serializable

@Serializable
data class CommonSelectionObj(
    val commonSelectionList: ArrayList<CommonSelectionModel?> = ArrayList(),
    val title: String = "",
    val isFromFuelType: Boolean = false,
):java.io.Serializable


class CommonSelectionBottomSheetFragment : BottomSheetDialogFragment() {
    private lateinit var mActivity: BaseActivity
    private lateinit var binding: FragmentCommonSelectionBottomSheetBinding
    private lateinit var viewModel: CommonSelectionBottomSheetViewModel
    var commonClick: CommonInterfaceClickEvent? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivity = activity as BaseActivity
        viewModel = ViewModelProvider(this)[CommonSelectionBottomSheetViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(
            LayoutInflater.from(requireContext()),
            R.layout.fragment_common_selection_bottom_sheet,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initializeFields()
        onClickListeners()
    }

    private fun initializeFields() {

        if (arguments?.containsKey("CommonSelectionObj") == true) {
            viewModel.commonSelectionObj = arguments?.getSerializableViaArgument("CommonSelectionObj", CommonSelectionObj::class.java)
        }

        binding.txtTitle.text = viewModel.commonSelectionObj?.title ?: ""
        viewModel.arrListCommonSelection.clear()
        viewModel.arrListCommonSelection = viewModel.commonSelectionObj?.commonSelectionList ?: arrayListOf()

        viewModel.adapterCommonSelection.onClickEvent = onCommonClickListener
        viewModel.adapterCommonSelection.isFromFuelType = viewModel.commonSelectionObj?.isFromFuelType == true
        binding.rvCommon.adapter = viewModel.adapterCommonSelection
        viewModel.updateCommonSelectionListAdapter()
    }

    private fun onClickListeners() {

        binding.ivClose.setOnClickListener {
            dialog?.dismiss()
        }

    }

    private var onCommonClickListener = object : CommonInterfaceClickEvent {
        override fun onItemClick(type: String, position: Int) {
            val model = viewModel.arrListCommonSelection[position]

            viewModel.arrListCommonSelection.forEach { it?.isSelected = false }
            viewModel.arrListCommonSelection[position]?.isSelected = true
            viewModel.updateCommonSelectionListAdapter()

            commonClick?.onItemClick("position",position)

            dialog?.dismiss()

        }

    }


}
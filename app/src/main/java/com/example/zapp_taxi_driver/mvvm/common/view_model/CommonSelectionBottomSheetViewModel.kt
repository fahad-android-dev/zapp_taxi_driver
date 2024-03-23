package com.example.zapp_taxi_driver.mvvm.common.view_model

import androidx.lifecycle.ViewModel
import com.example.zapp_taxi_driver.mvvm.common.adapter.CommonSelectionAdapter
import com.example.zapp_taxi_driver.mvvm.common.model.CommonSelectionModel
import com.example.zapp_taxi_driver.mvvm.common.view.CommonSelectionObj

class CommonSelectionBottomSheetViewModel:ViewModel() {


    var arrListCommonSelection = ArrayList<CommonSelectionModel?>()
    var adapterCommonSelection = CommonSelectionAdapter()

    fun updateCommonSelectionListAdapter() {
        adapterCommonSelection.setData(arrListCommonSelection)
    }

    var commonSelectionObj: CommonSelectionObj? = null

}
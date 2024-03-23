package com.example.zapp_taxi_driver.mvvm.driver_report.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.zapp_taxi_driver.mvvm.common.model.CommonSelectionModel
import com.example.zapp_taxi_driver.R
import com.example.zapp_taxi_driver.databinding.LvItemCommonListBinding
import com.example.zapp_taxi_driver.databinding.LvItemDriverReportBinding
import com.example.zapp_taxi_driver.helper.interfaces.CommonInterfaceClickEvent
import com.example.zapp_taxi_driver.mvvm.driver_report.model.DriverDetailDataModel
import com.example.zapp_taxi_driver.mvvm.driver_report.model.DriverReportResponseModel

class DriverReportAdapter : RecyclerView.Adapter<DriverReportAdapter.MyViewHolder>() {

    var arrCommonList: ArrayList<DriverDetailDataModel?> = ArrayList()
    var onClickEvent: CommonInterfaceClickEvent? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding: LvItemDriverReportBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.lv_item_driver_report,
            parent,
            false
        )
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val a = arrCommonList[position]

        holder.binding.txtName.text = a?.firstname
        holder.binding.txtDriverAvailTime.text = a?.total_time
        holder.binding.txtDate.text = a?.punchin_punchout_date

        holder.binding.root.setOnClickListener {
            onClickEvent?.onItemClick("",position)
        }

    }

    override fun getItemCount(): Int {
        return arrCommonList.size
    }

    class MyViewHolder(var binding: LvItemDriverReportBinding) :
        RecyclerView.ViewHolder(binding.root)

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: ArrayList<DriverDetailDataModel?>) {
        if (data.isNullOrEmpty()) {
            arrCommonList = ArrayList()
        }
        arrCommonList = data
        notifyDataSetChanged()
    }

}
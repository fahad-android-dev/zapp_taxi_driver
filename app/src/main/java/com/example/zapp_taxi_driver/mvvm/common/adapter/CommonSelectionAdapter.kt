package com.example.zapp_taxi_driver.mvvm.common.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.zapp_taxi_driver.mvvm.common.model.CommonSelectionModel
import com.example.zapp_taxi_driver.R
import com.example.zapp_taxi_driver.databinding.LvItemCommonListBinding
import com.example.zapp_taxi_driver.helper.interfaces.CommonInterfaceClickEvent

class CommonSelectionAdapter : RecyclerView.Adapter<CommonSelectionAdapter.MyViewHolder>() {

    var arrCommonList: ArrayList<CommonSelectionModel?> = ArrayList()
    var onClickEvent: CommonInterfaceClickEvent? = null
    var isFromFuelType : Boolean = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding: LvItemCommonListBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.lv_item_common_list,
            parent,
            false
        )
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val data = arrCommonList[position]

        holder.binding.txtTitle.text = if (isFromFuelType) data?.fuel_type else data?.name

        holder.binding.root.setOnClickListener {
            onClickEvent?.onItemClick("",position)
        }

    }

    override fun getItemCount(): Int {
        return arrCommonList.size
    }

    class MyViewHolder(var binding: LvItemCommonListBinding) :
        RecyclerView.ViewHolder(binding.root)

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: ArrayList<CommonSelectionModel?>) {
        if (data.isNullOrEmpty()) {
            arrCommonList = ArrayList()
        }
        arrCommonList = data
        notifyDataSetChanged()
    }

}
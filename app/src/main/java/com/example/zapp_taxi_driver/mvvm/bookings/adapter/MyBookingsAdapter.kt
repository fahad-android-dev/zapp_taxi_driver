package com.example.zapp_taxi_driver.mvvm.bookings.adapter

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
import com.example.zapp_taxi_driver.databinding.LvItemMyBookingsBinding
import com.example.zapp_taxi_driver.helper.Constants
import com.example.zapp_taxi_driver.helper.Global
import com.example.zapp_taxi_driver.helper.interfaces.CommonInterfaceClickEvent
import com.example.zapp_taxi_driver.mvvm.bookings.model.MyBookingsDataModel
import com.example.zapp_taxi_driver.mvvm.driver_report.model.DriverDetailDataModel
import com.example.zapp_taxi_driver.mvvm.driver_report.model.DriverReportResponseModel

class MyBookingsAdapter : RecyclerView.Adapter<MyBookingsAdapter.MyViewHolder>() {

    var arrCommonList: ArrayList<MyBookingsDataModel?> = ArrayList()
    var onClickEvent: CommonInterfaceClickEvent? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding: LvItemMyBookingsBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.lv_item_my_bookings,
            parent,
            false
        )
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val a = arrCommonList[position]

        val context = holder.itemView.context

        holder.binding.txtName.text = a?.name
        holder.binding.txtDate.text = Global.getFormattedDate(
            Constants.DATE_FORMAT,
            Constants.SimpleDateTimeFormat,
            a?.updated_date ?: "", Constants.NO_ZONE,
            context
        )
        holder.binding.txtBookingId.text = "${context.getString(R.string.booking_id_colon)} ${a?.id}"
        holder.binding.txtStatus.text = a?.status
        holder.binding.txtBookingType.text = "${context.getString(R.string.label_booking_type_colon)} ${a?.booking_type}"
        holder.binding.layoutLocation.tvSourceLocation.text = a?.source_address
        holder.binding.layoutLocation.tvDestinationLocation.text = a?.destination_address
        holder.binding.txtContact.text = a?.mobile_no

        holder.binding.root.setOnClickListener {
            onClickEvent?.onItemClick("bookingClicked",position)
        }

    }

    override fun getItemCount(): Int {
        return arrCommonList.size
    }

    class MyViewHolder(var binding: LvItemMyBookingsBinding) :
        RecyclerView.ViewHolder(binding.root)

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: ArrayList<MyBookingsDataModel?>) {
        if (data.isNullOrEmpty()) {
            arrCommonList = ArrayList()
        }
        arrCommonList = data
        notifyDataSetChanged()
    }

}
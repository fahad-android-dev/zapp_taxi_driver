package com.example.zapp_taxi_driver.mvvm.home.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.zapp_taxi_driver.R
import com.example.zapp_taxi_driver.databinding.FragmentHomeBinding
import com.example.zapp_taxi_driver.helper.BaseActivity
import com.example.zapp_taxi_driver.helper.BaseFragment


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
    ): View? {
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

        onClickListeners()
    }

    private fun onClickListeners(){
        binding.layoutHomeToolbar.imgDrawer.setOnClickListener {
            mActivity.openMainDrawers()
        }
    }

    private fun initializeFields(){

    }
}
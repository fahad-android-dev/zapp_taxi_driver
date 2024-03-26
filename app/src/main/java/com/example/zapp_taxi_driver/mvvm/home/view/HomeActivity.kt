package com.example.zapp_taxi_driver.mvvm.home.view

import AppNavigation.navigateToHome
import AppNavigation.navigateToLogin
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import com.example.zapp_taxi_driver.R
import com.example.zapp_taxi_driver.databinding.ActivityHomeBinding
import com.example.zapp_taxi_driver.databinding.NavHeaderLayoutBinding
import com.example.zapp_taxi_driver.helper.BaseActivity
import com.example.zapp_taxi_driver.helper.Dialogs
import com.example.zapp_taxi_driver.helper.Extensions.hideKeyboard
import com.example.zapp_taxi_driver.helper.Extensions.isInternetEnabled
import com.example.zapp_taxi_driver.helper.Extensions.printLog
import com.example.zapp_taxi_driver.helper.Global.loadImagesUsingCoil
import com.example.zapp_taxi_driver.helper.Global.showSnackBar
import com.example.zapp_taxi_driver.helper.PrefUtils.getUserDataResponse
import com.example.zapp_taxi_driver.helper.PrefUtils.getUserId
import com.example.zapp_taxi_driver.helper.PrefUtils.setUserDataResponse
import com.example.zapp_taxi_driver.helper.ShareDetails.initSaveDeepLink
import com.example.zapp_taxi_driver.helper.interfaces.AlertDialogInterface
import com.example.zapp_taxi_driver.helper.location.LocationService
import com.example.zapp_taxi_driver.mvvm.home.model.UserProfileRequestModel
import com.example.zapp_taxi_driver.mvvm.home.view_model.HomeViewModel
import com.example.zapp_taxi_driver.mvvm.login.view_model.LoginViewModel
import com.pushwoosh.Pushwoosh
import kotlinx.coroutines.launch
import org.json.JSONObject

class HomeActivity : BaseActivity() {

    lateinit var binding: ActivityHomeBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var headerLayout: NavHeaderLayoutBinding
    private var isBackPressed: Long = 0

    private val navController by lazy {
        Navigation.findNavController(this, R.id.nav_host_fragment)
    }

    override fun onStart() {
        super.onStart()
        /** after adding branch key in strings uncomment this*/
        /*Branch.sessionBuilder(this)
            .withData(this.intent.data)
            .withCallback { referringParams, error ->
                initSaveDeepLink(referringParams, error)
            }.init()*/
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        /** after adding branch key in strings uncomment this*/
        /*Branch.sessionBuilder(this)
            .withData(intent?.data)
            .withCallback { referringParams, error ->
                initSaveDeepLink(referringParams, error)
            }.reInit()*/
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)

        initLeftNavMenuDrawer()
        initializeFields()
        onClickListeners()
        initObserver()
    }

    private fun initLeftNavMenuDrawer() {
        binding.navigationView.setupWithNavController(navController)
        headerLayout = binding.headerLayout

        binding.rootLayout.addDrawerListener(object : DrawerLayout.DrawerListener {
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
               binding.rootLayout.hideKeyboard()
            }
            override fun onDrawerOpened(drawerView: View) {}
            override fun onDrawerClosed(drawerView: View) {}
            override fun onDrawerStateChanged(newState: Int) {}
        })

        onLeftNavMenuDrawerClickListener()
    }

    private fun onLeftNavMenuDrawerClickListener() {
        headerLayout.conHome.setOnClickListener {
            navigateToHome {}
        }

        headerLayout.conShareApp.setOnClickListener {
            binding.rootLayout.closeDrawers()
            val sendIntent = Intent()
            sendIntent.setAction(Intent.ACTION_SEND)
            sendIntent.putExtra(
                Intent.EXTRA_TEXT,
                """Hey check out this ${getString(R.string.app_name)} cab driver mobile app

Get Cabs bookings for local,rental and outstation travels.

 Available in Playstore Now : https://play.google.com/store/apps/details?id=$packageName"""
            )
            sendIntent.setType("text/plain")
            startActivity(sendIntent)
        }

        headerLayout.conLogout.setOnClickListener {
            Dialogs.showCustomAlert(
                activity= this@HomeActivity,
                title= getString(R.string.label_sign_out_message),
                msg = getString(R.string.label_are_you_sure_want_to_sign_out),
                yesBtn= getString(R.string.label_yes),
                noBtn= getString(R.string.label_no),
                alertDialogInterface = object : AlertDialogInterface {
                    override fun onYesClick() {
                        setUserDataResponse(null)
                        navigateToLogin(true)
                        finishAffinity()
                    }

                    override fun onNoClick() {}
                },
            )
        }

        headerLayout.conDriverReport.setOnClickListener {
            binding.rootLayout.closeDrawers()
            findNavController(R.id.nav_host_fragment).navigate(R.id.action_to_navigation_driver_report)
        }
        headerLayout.conMyProfile.setOnClickListener {
            binding.rootLayout.closeDrawers()
            findNavController(R.id.nav_host_fragment).navigate(R.id.action_to_navigation_my_profile)
        }
    }


    override fun onNavigateUp(): Boolean {
        return navController.navigateUp(
            AppBarConfiguration(
                topLevelDestinationIds = setOf(
                    R.id.nav_graph_home,
                    R.id.nav_graph_2,
                    R.id.nav_graph_3,
                    R.id.nav_graph_4
                ), fallbackOnNavigateUpListener = ::onSupportNavigateUp
            )
        )
    }

    private fun onClickListeners() {

    }

    private fun initializeFields() {
        ifLocationPermissionIsEnabled{
            Intent(applicationContext, LocationService::class.java).apply {
                action = LocationService.ACTION_START
                startService(this)
            }
        }

        callUserProfileApi()
        getIntentData()
        onBackPressedDispatcher.addCallback(this@HomeActivity, object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                onBackPressedCallback()
            }
        })
    }

    private fun getIntentData() {
        if (intent.hasExtra(Pushwoosh.PUSH_RECEIVE_EVENT)) {
            val referringParams = JSONObject(intent.getStringExtra(Pushwoosh.PUSH_RECEIVE_EVENT).toString())
            referringParams.toString().printLog("PUSH DETAILS")
            initSaveDeepLink(referringParams , null)
        }
    }



    fun onBackPressedCallback() {
        if (navController.currentDestination?.id == R.id.navigation_home) {
            if (isBackPressed + 2000 > System.currentTimeMillis()) {
                finish()
            } else {
                binding.root.showSnackBar(getString(R.string.press_back_message))
                isBackPressed = System.currentTimeMillis()
            }
        } else {
            navController.popBackStack()
        }
    }

    fun openMainDrawers() {
        binding.rootLayout.open()
    }

     private fun initObserver() {
             viewModel.mutUserProfileResponse.observe(this) {
                    hideProgressDialog()
                     lifecycleScope.launch {
                         if (it != null) {
                             if (it.code == 200) {
                                 headerLayout.imgLogo.loadImagesUsingCoil("http://68.183.92.60/Zap_taxi/assets/Upload/driver_detail/${it.data?.profile_image}")
                             } else {
                                 binding.root.showSnackBar(getString(R.string.error_message))
                             }
                         } else {
                             binding.root.showSnackBar(getString(R.string.error_message))
                         }
                     }
             }

         }

    private fun callUserProfileApi(){
        isInternetEnabled{
            viewModel.userProfileApi(
                model = UserProfileRequestModel(
                    id = getUserId(),
                    AuthToken = getUserDataResponse()?.AuthToken
                )

            )
        }
    }

}
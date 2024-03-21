package com.example.zapp_taxi_driver.mvvm.home.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import com.example.zapp_taxi_driver.R
import com.example.zapp_taxi_driver.databinding.ActivityHomeBinding
import com.example.zapp_taxi_driver.databinding.NavHeaderLayoutBinding
import com.example.zapp_taxi_driver.helper.BaseActivity
import com.example.zapp_taxi_driver.helper.Constants
import com.example.zapp_taxi_driver.helper.Extensions.hideKeyboard
import com.example.zapp_taxi_driver.helper.Extensions.printLog
import com.example.zapp_taxi_driver.helper.Global.showSnackBar
import com.example.zapp_taxi_driver.helper.ShareDetails.initSaveDeepLink
import com.example.zapp_taxi_driver.helper.interfaces.CommonInterfaceClickEvent
import com.pushwoosh.Pushwoosh
import org.json.JSONObject

class HomeActivity : BaseActivity() {

    lateinit var binding: ActivityHomeBinding
    private lateinit var headerLayout: NavHeaderLayoutBinding
    private var isBackPressed: Long = 0
    private var currentMenuItemId: Int? = 0

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
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        initBottomTabs()
        initLeftNavMenuDrawer()
        initializeFields()
        initializeToolbar()
        onClickListeners()
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
        headerLayout.ivBackArrow.setOnClickListener {
            binding.rootLayout.closeDrawers()
        }
    }

    private fun initBottomTabs() {
        binding.bottomNavigationView.setupWithNavController(navController)
        onBottomNavigationItemClickListener()
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

    private fun initializeToolbar() {
        toolbarInit(getString(R.string.app_name))
    }

    private fun initializeFields() {
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
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
    }

    private fun toolbarInit(title: String){
        setUpToolbar(binding.layoutToolbar,
            title = title,
            isBackArrow = false,
            toolbarClickListener = object : CommonInterfaceClickEvent {
                override fun onToolBarListener(type: String) {
                    if (type == Constants.TOOLBAR_ICON_ONE){

                    }
                    if (type == Constants.TOOLBAR_ICON_TWO){

                    }
                }
            }
        )
    }

    private fun onBottomNavigationItemClickListener() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            currentMenuItemId = destination.id
            when (destination.id) {
                R.id.navigation_home -> {
                    toolbarInit(getString(R.string.app_name))
                }

                else -> {
                    setUpToolbar(binding.layoutToolbar, title = getString(R.string.app_name), isBackArrow = false)
                }
            }
        }
    }
}
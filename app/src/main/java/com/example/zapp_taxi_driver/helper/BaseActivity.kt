package com.example.zapp_taxi_driver.helper

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.zapp_taxi_driver.R
import com.example.zapp_taxi_driver.databinding.LayoutToolbarBinding
import com.example.zapp_taxi_driver.helper.Extensions.isVisibleInvisible
import com.example.zapp_taxi_driver.helper.PrefUtils.getAppConfig
import com.example.zapp_taxi_driver.helper.interfaces.CommonInterfaceClickEvent
import java.util.*


open class BaseActivity : AppCompatActivity() {
    private var progressDialog: Dialog? = null
    private var onRequestPermissionsResult: OnRequestPermissionsResult? = null
    private var layoutToolbarBinding: LayoutToolbarBinding? = null

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(newBase)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val config = newBase.resources.configuration
            config.setLocale(Locale(this.getAppConfig()?.lang ?: "en"))
            applyOverrideConfiguration(config)
        }
    }

    fun showProgressDialog(type: String = "") {
        if (NetworkUtil.getConnectivityStatus(this)) {
            if (progressDialog != null && progressDialog?.isShowing == true) {
                progressDialog?.dismiss()
            }
            val v: View = when (type) {
                "B" -> {
                    //for bottom tab
                    layoutInflater.inflate(R.layout.dialog_loading_bottom_bar, null)
                }
                "F" -> {
                    //for full screen
                    layoutInflater.inflate(R.layout.dialog_loading_full_screen, null)
                }
                else -> {
                    //for toolbar
                    layoutInflater.inflate(R.layout.dialog_loading, null)
                }
            }

            progressDialog = Dialog(this, R.style.MyDialogTheme)
            progressDialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
            progressDialog?.setContentView(v)


            progressDialog?.window?.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )

            if (progressDialog != null && progressDialog?.isShowing == false && !isFinishing) {
                progressDialog?.show()
                progressDialog?.setCancelable(false)
                progressDialog?.setCanceledOnTouchOutside(false)
            }
        }
    }

    fun hideProgressDialog() {
        if (progressDialog != null && progressDialog?.isShowing == true) {
            progressDialog?.dismiss()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        onRequestPermissionsResult?.onResultPermissionResult(requestCode, permissions, grantResults)
    }


    fun ifCameraIsEnabled(block: () -> Unit) {
        if (Permission.isCameraCanAccess(this)) {
            block()
        } else {
            Permission.requestCameraPermission(this)
            onRequestPermissionsResult = object : OnRequestPermissionsResult {
                override fun onResultPermissionResult(
                    requestCode: Int,
                    permissions: Array<out String>,
                    grantResults: IntArray
                ) {
                    if (requestCode == Permission.CAMERA_REQUEST_CODE) {
                        if (Permission.isCameraCanAccess(this@BaseActivity)) {
                            block()
                        } else {
                            Permission.showAlertToEnablePermission(
                                this@BaseActivity,
                                strTitle = resources.getString(R.string.enable_camera_to_continue)
                            )
                        }
                    }
                }
            }
        }
    }

    fun ifGalleryIsEnabled(block: () -> Unit) {
        if (Permission.isGalleryCanAccess(this)) {
            block()
        } else {
            Permission.requestGalleryPermission(this)
            onRequestPermissionsResult = object : OnRequestPermissionsResult {
                override fun onResultPermissionResult(
                    requestCode: Int,
                    permissions: Array<out String>,
                    grantResults: IntArray
                ) {
                    if (requestCode == Permission.GALLERY_REQUEST_CODE) {
                        if (Permission.isGalleryCanAccess(this@BaseActivity)) {
                            block()
                        } else {
                            Permission.showAlertToEnablePermission(
                                this@BaseActivity,
                                strTitle = resources.getString(R.string.enable_storage_to_continue)
                            )
                        }
                    }
                }
            }
        }
    }

    fun ifStoragePermissionIsEnabled(block: () -> Unit) {
        if (Permission.isStorageCanAccess(this)) {
            block()
        } else {
            Permission.requestStoragePermission(this)
            onRequestPermissionsResult = object : OnRequestPermissionsResult {
                override fun onResultPermissionResult(
                    requestCode: Int,
                    permissions: Array<out String>,
                    grantResults: IntArray
                ) {
                    if (requestCode == Permission.STORAGE_REQUEST_CODE) {
                        if (Permission.isStorageCanAccess(this@BaseActivity)) {
                            block()
                        } else {
                            Permission.showAlertToEnablePermission(
                                this@BaseActivity,
                                strTitle = resources.getString(R.string.enable_storage_to_continue)
                            )
                        }
                    }
                }
            }
        }
    }

    fun setUpToolbar(
        binding: LayoutToolbarBinding,
        title: String = "",
        iconOne: Int = 0,
        iconTwo: Int = 0,
        isBackArrow: Boolean = true,
        toolbarClickListener: CommonInterfaceClickEvent? = null
    ) {
        layoutToolbarBinding = binding

        if (isBackArrow) layoutToolbarBinding?.conIconOne?.visibility = isVisibleInvisible(iconOne != 0)
        else layoutToolbarBinding?.conIconOne?.isVisible = iconOne != 0

        if (layoutToolbarBinding?.conIconOne?.isVisible == true)
            layoutToolbarBinding?.ivIconOne?.setImageResource(iconOne)

        layoutToolbarBinding?.conIconTwo?.isVisible = iconTwo != 0
        if (layoutToolbarBinding?.conIconTwo?.isVisible == true)
            layoutToolbarBinding?.ivIconTwo?.setImageResource(iconTwo)

        layoutToolbarBinding?.linBackArrow?.isVisible = isBackArrow
        layoutToolbarBinding?.linBackArrow?.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
        layoutToolbarBinding?.conIconOne?.setOnClickListener {
            toolbarClickListener?.onToolBarListener(Constants.TOOLBAR_ICON_ONE)
        }

        layoutToolbarBinding?.conIconTwo?.setOnClickListener {
            toolbarClickListener?.onToolBarListener(Constants.TOOLBAR_ICON_TWO)
        }
        layoutToolbarBinding?.txtToolbarHeader?.text = title
    }

    fun ifLocationPermissionIsEnabled(block: () -> Unit) {
        if (Permission.isLocationCanAccess(this)) {
            block()
        } else {
            Permission.requestLocationPermission(this)
            onRequestPermissionsResult = object : OnRequestPermissionsResult {
                override fun onResultPermissionResult(
                    requestCode: Int,
                    permissions: Array<out String>,
                    grantResults: IntArray
                ) {
                    if (requestCode == Permission.LOCATION_REQUEST_CODE) {
                        if (Permission.isLocationCanAccess(this@BaseActivity)) {
                            block()
                        } else {
                            Permission.showAlertToEnablePermission(
                                this@BaseActivity,
                                strTitle = resources.getString(R.string.enable_location_to_continue)
                            )
                        }
                    }
                }
            }
        }
    }
}
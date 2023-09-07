package com.shiki.koko.amap

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import com.shiki.koko.amap.databinding.ActivityAmapBinding
import com.shiki.koko.base.IntentLauncher
import com.shiki.koko.base.PermissionLauncher
import com.shiki.koko.base.permissionProcess
import com.shiki.koko.base.settingManageExternalStorage
import com.shiki.koko.base.toast

class AMapActivity : AppCompatActivity() {

    private val TAG = "AMapActivity"
    private lateinit var binding: ActivityAmapBinding
    private lateinit var permissionLauncher: PermissionLauncher
    private lateinit var intentLauncher: IntentLauncher

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e(TAG, "onCreate")
        binding = ActivityAmapBinding.inflate(layoutInflater)
        setContentView(binding.root)
        permissionLauncher = PermissionLauncher(this)
        intentLauncher = IntentLauncher(this)
        binding.btnPrivacy.setOnClickListener {
            updatePrivacyShow(this)
            updatePrivacyAgree(this)
        }
        binding.btnPrivacy.callOnClick()
        binding.btnPermission.setOnClickListener {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                if (!Environment.isExternalStorageManager()) {
                    val uri = Uri.parse("package:$packageName")
                    val intent = settingManageExternalStorage(uri)
                    intentLauncher.launch(intent) {
                        val flag = Environment.isExternalStorageManager()
                        if (flag) {
                            toast("已设置额外储存管理")
                        } else {
                            toast("未设置额外储存管理")
                        }
                    }
                } else {
//                    toast("已设置额外储存管理")
                }
            } else {
                permissionLauncher.permissionProcess(this,
                    EXTERNAL_STORAGE_PERMISSION,
                    agreeAction = { toast("权限同意") },
                    denyAction = { toast("权限拒绝") },
                    denyNotAskAction = { toast("权限拒绝且不再询问") }
                )
            }

            permissionLauncher.permissionProcess(this, PERMISSIONS,
                agreeAction = {
//                    toast("权限同意")
                },
                denyAction = { toast("权限拒绝") },
                denyNotAskAction = { toast("权限拒绝且不再询问") })
        }
        binding.btnPermission.callOnClick()

        binding.btnShowMap.setOnClickListener {
            val intent = Intent(this, AMapShowActivity::class.java).apply {
//                putExtra("key", "MAP_TYPE_NORMAL")
            }
            startActivity(intent)
        }
        binding.btnLocation.setOnClickListener {
            val intent = Intent(this, LocationActivity::class.java).apply {
                putExtra("key", "Location")
            }
            startActivity(intent)
        }
        binding.btnIndoorMap.setOnClickListener {
            val intent = Intent(this, IndoorMapActivity::class.java).apply {
//                putExtra("key", "MAP_TYPE_NORMAL")
            }
            startActivity(intent)
        }
        binding.btnGestureMap.setOnClickListener {
            val intent = Intent(this, UiSettingsActivity::class.java).apply {
                putExtra("key", "Gesture")
            }
            startActivity(intent)
        }
        binding.btnPoiMap.setOnClickListener {
            val intent = Intent(this, PoiActivity::class.java).apply {}
            startActivity(intent)
        }

    }

    override fun onResume() {
        super.onResume()
        Log.e(TAG, "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.e(TAG, "onPause")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e(TAG, "onDestroy")
    }

    companion object {
        val EXTERNAL_STORAGE_PERMISSION = arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
        )
        val PERMISSIONS = arrayOf(
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.CHANGE_WIFI_STATE,
//            Manifest.permission.WRITE_SETTINGS,//无法获取
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
    }


}
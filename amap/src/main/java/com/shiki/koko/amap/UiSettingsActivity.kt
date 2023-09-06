package com.shiki.koko.amap

import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import androidx.appcompat.app.AppCompatActivity
import com.amap.api.maps.AMap
import com.amap.api.maps.UiSettings
import com.shiki.koko.amap.databinding.ActivityUiSettingsBinding

/**
 * 地图 UiSettings 相关功能
 * 1.手势功能
 */
class UiSettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUiSettingsBinding
    private lateinit var aMap: AMap
    private lateinit var mUiSettings: UiSettings

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUiSettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.aMap.onCreate(savedInstanceState)
        //地图对象
        aMap = binding.aMap.map
        mUiSettings = aMap.uiSettings

        when (intent.getStringExtra("key")) {
            "Gesture" -> gesture()
        }
    }

    /**
     * 地图手势相关
     */
    private fun gesture() {
        binding.llGesture.visibility = View.VISIBLE
        binding.scrollToggle.setOnClickListener {
            mUiSettings.isScrollGesturesEnabled = (it as CheckBox).isChecked
        }
        binding.zoomGesturesToggle.setOnClickListener {
            mUiSettings.isZoomGesturesEnabled = (it as CheckBox).isChecked
        }
        //地图是否可以倾斜
        binding.tiltToggle.setOnClickListener {
            mUiSettings.isTiltGesturesEnabled = (it as CheckBox).isChecked
        }
        binding.rotateToggle.setOnClickListener {
            mUiSettings.isRotateGesturesEnabled = (it as CheckBox).isChecked
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.aMap.onDestroy()
    }

    override fun onResume() {
        super.onResume()
        binding.aMap.onResume()
    }


    override fun onPause() {
        super.onPause()
        binding.aMap.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        binding.aMap.onSaveInstanceState(outState)
    }

}
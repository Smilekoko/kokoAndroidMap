package com.shiki.koko.amap

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.amap.api.maps.AMap
import com.amap.api.maps.CameraUpdate
import com.amap.api.maps.CameraUpdateFactory
import com.amap.api.maps.model.BitmapDescriptorFactory
import com.amap.api.maps.model.CameraPosition
import com.amap.api.maps.model.MarkerOptions
import com.shiki.koko.amap.databinding.ActivityCameraBinding

/**
 * 地图视角相机
 */
class CameraActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCameraBinding
    private lateinit var aMap: AMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.aMap.onCreate(savedInstanceState)
        aMap = binding.aMap.map

        binding.LuJiaZui.setOnClickListener {
            changeCamera(
                CameraUpdateFactory.newCameraPosition(
                    CameraPosition(Constants.SHANGHAI, 18f, 30f, 0f)
                )
            )
            aMap.clear()
            aMap.addMarker(
                MarkerOptions().position(Constants.SHANGHAI)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
            )
        }
        binding.ZhongGuanCun.setOnClickListener {
            changeCamera(
                CameraUpdateFactory.newCameraPosition(
                    CameraPosition(Constants.ZHONGGUANCUN, 18f, 30f, 30f)
                )
            )
            aMap.clear()
            aMap.addMarker(
                MarkerOptions().position(Constants.ZHONGGUANCUN)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
            )
        }
    }

    override fun onResume() {
        super.onResume()
        binding.aMap.onResume()
    }

    override fun onPause() {
        super.onPause()
        binding.aMap.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.aMap.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        binding.aMap.onSaveInstanceState(outState)
    }

    /**
     * 根据动画按钮状态，调用函数animateCamera或moveCamera来改变可视区域
     */
    private fun changeCamera(update: CameraUpdate) {
        aMap.moveCamera(update)
    }
}
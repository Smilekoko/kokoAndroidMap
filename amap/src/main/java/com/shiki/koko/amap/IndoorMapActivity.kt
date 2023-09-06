package com.shiki.koko.amap

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.amap.api.maps.AMap
import com.amap.api.maps.CameraUpdateFactory
import com.amap.api.maps.model.LatLng
import com.shiki.koko.amap.databinding.ActivityIndoorMapBinding

//室内地图
class IndoorMapActivity : AppCompatActivity() {

    private lateinit var binding: ActivityIndoorMapBinding
    private lateinit var aMap: AMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIndoorMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.aMap.onCreate(savedInstanceState)
        aMap = binding.aMap.map

        aMap.uiSettings.isScaleControlsEnabled = true

        //加载监听器
        aMap.setOnMapLoadedListener {

            // 室内地图默认不显示，这里把它设置成显示
            aMap.showIndoorMap(true)
            // 关闭SDK自带的室内地图控件
            aMap.uiSettings.isIndoorSwitchEnabled = true

            //移动到有室内地图的地方,放大级别才可以看见
            aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(39.91095, 116.37296), 19f))
        }

        //设置室内回调监听
        aMap.setOnIndoorBuildingActiveListener { indoorBuildingInfo ->

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
}
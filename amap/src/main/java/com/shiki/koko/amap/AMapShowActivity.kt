package com.shiki.koko.amap

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.amap.api.maps.AMap
import com.amap.api.maps.model.CustomMapStyleOptions
import com.shiki.koko.amap.databinding.ActivityAmapShowBinding
import com.shiki.koko.amap.mapstyle.assetsMapStyle

//显示地图
class AMapShowActivity : AppCompatActivity() {

    private val TAG = "AMapShowActivity"
    private lateinit var binding: ActivityAmapShowBinding
    private lateinit var aMap: AMap

    //地图样式选项
    private val mapStyleOptions = CustomMapStyleOptions()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAmapShowBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
        binding.aMap.onCreate(savedInstanceState)
        //地图对象
        aMap = binding.aMap.map

        binding.btnBasicMap.setOnClickListener {
            aMap.mapType = AMap.MAP_TYPE_NORMAL // 矢量地图模式
        }
        binding.btnSatelliteMap.setOnClickListener {
            aMap.mapType = AMap.MAP_TYPE_SATELLITE // 卫星地图模式
        }
        binding.btnNightMap.setOnClickListener {
            aMap.mapType = AMap.MAP_TYPE_NIGHT //夜景地图模式
        }
        binding.btnNaviMap.setOnClickListener {
            aMap.mapType = AMap.MAP_TYPE_NAVI //导航地图模式
        }

        binding.checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
            // 设置自定义样式
            mapStyleOptions.isEnable = isChecked
            // mapStyleOptions.setStyleId("your id");
            aMap.setCustomMapStyle(mapStyleOptions)
        }

        initCustomMapStyle()
    }

    //初始化自定义样式
    private fun initCustomMapStyle() {
        // 设置自定义样式
        mapStyleOptions.styleData = assetsMapStyle("style.data")
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
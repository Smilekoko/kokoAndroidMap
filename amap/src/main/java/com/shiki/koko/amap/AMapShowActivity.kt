package com.shiki.koko.amap

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationListener
import com.amap.api.maps.AMap
import com.shiki.koko.amap.databinding.ActivityAmapShowBinding
import com.shiki.koko.base.toast

class AMapShowActivity : AppCompatActivity() {

    private val TAG = "AMapShowActivity"
    private lateinit var binding: ActivityAmapShowBinding
    private lateinit var aMap: AMap

    private lateinit var locationChangeListener: AMap.OnMyLocationChangeListener
    private lateinit var aMapLocationListener: AMapLocationListener
    private lateinit var aMapLocationClient: AMapLocationClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAmapShowBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
        binding.aMap.onCreate(savedInstanceState)
        //地图对象
        aMap = binding.aMap.map

        initListener()
    }

    private fun initListener() {
        aMapLocationClient = AMapLocationClient(this.application)

        aMapLocationListener = AMapLocationListener {

        }

        aMapLocationClient.setLocationListener(aMapLocationListener)

        locationChangeListener = AMap.OnMyLocationChangeListener { location ->
            toast("location ${location.accuracy} ${location.altitude} ${location.longitude}")
        }


    }

    override fun onDestroy() {
        super.onDestroy()
        binding.aMap.onDestroy()
    }

    override fun onResume() {
        super.onResume()
        binding.aMap.onResume()
        val bundle = intent.getStringExtra("Location") ?: "null"
        toast(bundle)
        when (bundle) {
            "Location" -> {
                locationBlue(aMap)
            }
        }
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
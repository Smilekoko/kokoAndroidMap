package com.shiki.koko.amap

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationListener
import com.amap.api.maps.AMap
import com.shiki.koko.amap.databinding.ActivityAmapShowBinding
import com.shiki.koko.amap.location.getLocationOption
import com.shiki.koko.amap.location.printLocationInfo
import com.shiki.koko.base.toast

class AMapShowActivity : AppCompatActivity() {

    private val TAG = "AMapShowActivity"
    private lateinit var binding: ActivityAmapShowBinding
    private lateinit var aMap: AMap

    //定位客户端
    private lateinit var aMapLocationClient: AMapLocationClient

    //定位结果监听
    private lateinit var aMapLocationListener: AMapLocationListener

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
                location()
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


    //定位
    private fun location() {

        val result = kotlin.runCatching {
            aMapLocationClient = AMapLocationClient(this.application)
            //设置定位监听
            aMapLocationListener = AMapLocationListener {
                binding.tvLog.visibility = View.VISIBLE
                binding.tvLog.text = printLocationInfo(it)
            }
            aMapLocationClient.setLocationListener(aMapLocationListener)

            //设置定位参数
            val option = getLocationOption()
            aMapLocationClient.setLocationOption(option)

            //启动定位
            aMapLocationClient.startLocation()
        }
        result.onFailure {
            toast("定位异常")
        }

    }

}
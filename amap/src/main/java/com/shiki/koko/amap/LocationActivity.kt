package com.shiki.koko.amap

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationListener
import com.shiki.koko.amap.databinding.ActivityLocationBinding
import com.shiki.koko.amap.location.getLocationOption
import com.shiki.koko.amap.location.printLocationInfo
import com.shiki.koko.base.toast


//定位功能
class LocationActivity : AppCompatActivity() {


    //定位客户端
    private lateinit var aMapLocationClient: AMapLocationClient

    //定位结果监听
    private lateinit var aMapLocationListener: AMapLocationListener


    private lateinit var binding: ActivityLocationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLocationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        location()
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
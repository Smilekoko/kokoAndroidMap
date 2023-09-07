package com.shiki.koko.amap

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.amap.api.maps.AMap
import com.amap.api.maps.AMap.OnMarkerClickListener
import com.amap.api.maps.AMap.OnPOIClickListener
import com.amap.api.maps.AMapException
import com.amap.api.maps.AMapUtils
import com.amap.api.maps.model.BitmapDescriptorFactory
import com.amap.api.maps.model.Marker
import com.amap.api.maps.model.MarkerOptions
import com.amap.api.maps.model.NaviPara
import com.amap.api.maps.model.Poi
import com.shiki.koko.amap.databinding.ActivityPoiBinding

/**
 * 兴趣点
 */
class PoiActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPoiBinding
    private lateinit var aMap: AMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPoiBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.aMap.onCreate(savedInstanceState)
        aMap = binding.aMap.map
        aMap.setOnPOIClickListener { poi ->
            aMap.clear()
            val markerOptions = MarkerOptions()
            markerOptions.position(poi.coordinate)
            val textView = TextView(applicationContext)
            textView.text = "到" + poi.name + "去"
            textView.gravity = Gravity.CENTER
            textView.setTextColor(Color.BLACK)
            textView.setBackgroundResource(R.drawable.custom_info_bubble)
            markerOptions.icon(BitmapDescriptorFactory.fromView(textView))
            aMap.addMarker(markerOptions)
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
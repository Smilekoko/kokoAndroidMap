package com.shiki.koko.amap

import android.content.Context
import android.location.Location
import android.location.LocationListener
import com.amap.api.location.AMapLocation
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationListener
import com.amap.api.maps.AMap
import com.amap.api.maps.model.MyLocationStyle
import com.amap.api.maps.model.MyLocationStyle.LOCATION_TYPE_FOLLOW

//定位蓝点
//https://lbs.amap.com/api/android-sdk/guide/create-map/mylocation#s4

fun locationBlue(aMap: AMap) {

    //初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);
    //连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
    val myLocationStyle = MyLocationStyle()
    myLocationStyle.myLocationType(LOCATION_TYPE_FOLLOW)
    myLocationStyle.showMyLocation(true)

    //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
    myLocationStyle.interval(2000)

    //设置定位蓝点的Style
    aMap.myLocationStyle = myLocationStyle

    //aMap.getUiSettings().setMyLocationButtonEnabled(true);设置默认定位按钮是否显示，非必需设置。
    aMap.isMyLocationEnabled = true // 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。

}
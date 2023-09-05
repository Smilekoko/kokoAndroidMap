package com.shiki.koko.amap.location

import android.util.Log
import com.amap.api.location.AMapLocation
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationClientOption.AMapLocationMode
import com.amap.api.location.AMapLocationClientOption.AMapLocationProtocol
import com.amap.api.location.AMapLocationQualityReport
import com.shiki.koko.base.toTime

//定位蓝点
//https://lbs.amap.com/api/android-sdk/guide/create-map/mylocation#s4

//设置定位参数
fun getLocationOption(): AMapLocationClientOption {
    val mOption = AMapLocationClientOption()

//    locationOption.locationMode = AMapLocationMode.Battery_Saving //低功耗模式
//    locationOption.locationMode = AMapLocationMode.Device_Sensors //传感器模式
    mOption.locationMode = AMapLocationMode.Hight_Accuracy //高精度模式
    mOption.isGpsFirst = true //可选，设置是否gps优先，只在高精度模式下有效。默认关闭
    mOption.httpTimeOut = 30000 //可选，设置网络请求超时时间。默认为30秒。在仅设备模式下无效
    mOption.interval = 2000 //可选，设置定位间隔。默认为2秒
    mOption.isNeedAddress = true //可选，设置是否返回逆地理地址信息。默认是true
    mOption.isOnceLocation = false //可选，设置是否单次定位。默认是false
    mOption.isOnceLocationLatest = false //可选，设置是否等待wifi刷新，默认为false.如果设置为true,会自动变为单次定位，持续定位时不要使用
    AMapLocationClientOption.setLocationProtocol(AMapLocationProtocol.HTTP) //可选， 设置网络请求的协议。可选HTTP或者HTTPS。默认为HTTP
    mOption.isSensorEnable = true //可选，设置是否使用传感器。默认是false
    mOption.isWifiScan = true //可选，设置是否开启wifi扫描。默认为true，如果设置为false会同时停止主动刷新，停止以后完全依赖于系统刷新，定位位置可能存在误差
    mOption.isLocationCacheEnable = true //可选，设置是否使用缓存定位，默认为true
    mOption.geoLanguage =
        AMapLocationClientOption.GeoLanguage.DEFAULT //可选，设置逆地理信息的语言，默认值为默认语言（根据所在地区选择语言）
    return mOption
}

//打印定位信息
fun printLocationInfo(location: AMapLocation?): String {
    if (location != null) {
        val sb = StringBuffer()
        //errCode等于0代表定位成功，其他的为定位失败，具体的可以参照官网定位错误码说明
        if (location.errorCode == 0) {
            Log.e("AMapLocation", location.toString())
            sb.append("定位成功\n")
            sb.append("定位类型:${location.locationType}\n")
            sb.append("经度:${location.longitude}\n")
            sb.append("纬度:${location.latitude}\n")
            sb.append("精度:${location.accuracy}\n")
            sb.append("提供者:${location.provider}\n")

            sb.append("速度:${location.speed}米/秒\n")
            sb.append("角度:${location.bearing}米/秒\n")

            //获取当前提供定位服务的卫星个数
            sb.append("卫星个数：${location.satellites}\n")
            sb.append("国家：${location.country}\n")
            sb.append("省：${location.province}\n")
            sb.append("市：${location.city}\n")
            sb.append("城市邮政编码：${location.cityCode}\n")
            sb.append("区 ：${location.district}\n")
            sb.append("区域码：${location.adCode}\n")
            sb.append("地址：${location.address}\n")
            sb.append("兴趣点：${location.poiName}\n")

            sb.append("定位时间:  ${System.currentTimeMillis().toTime()}\n")

        } else {
            sb.append("定位失败\n")
            sb.append("错误码：${location.errorCode}\n")
            sb.append("错误信息：${location.errorInfo}\n")
            sb.append("错误描述：${location.locationDetail}\n")
        }
        sb.append("***定位质量报告***\n")
        sb.append("* WIFI开关：${if (location.locationQualityReport.isWifiAble) "开启" else "关闭"}\n")
        sb.append("* GPS状态：${getGPSStatusString(location.locationQualityReport.gpsStatus)}\n")
        sb.append("* GPS卫星数：${location.locationQualityReport.gpsSatellites}\n")
        sb.append("* 网络类型：${location.locationQualityReport.networkType}\n")
        sb.append("* 网络耗时：${location.locationQualityReport.netUseTime}\n")
        sb.append("****************\n")
        return sb.toString()
    } else {
        return ""
    }
}


/**
 * 获取GPS状态的字符串
 * @param statusCode GPS状态码
 * @return
 */
private fun getGPSStatusString(statusCode: Int): String? {
    var str = ""
    when (statusCode) {
        AMapLocationQualityReport.GPS_STATUS_OK -> str = "GPS状态正常"
        AMapLocationQualityReport.GPS_STATUS_NOGPSPROVIDER -> str =
            "手机中没有GPS Provider，无法进行GPS定位"

        AMapLocationQualityReport.GPS_STATUS_OFF -> str = "GPS关闭，建议开启GPS，提高定位质量"
        AMapLocationQualityReport.GPS_STATUS_MODE_SAVING -> str =
            "选择的定位模式中不包含GPS定位，建议选择包含GPS定位的模式，提高定位质量"

        AMapLocationQualityReport.GPS_STATUS_NOGPSPERMISSION -> str =
            "没有GPS定位权限，建议开启gps定位权限"
    }
    return str
}

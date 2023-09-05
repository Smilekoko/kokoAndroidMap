package com.shiki.koko.amap.mapstyle

import android.content.Context
import java.io.IOException
import java.io.InputStream

//样式

//地图样式
fun Context.assetsMapStyle(style: String): ByteArray? {
    val styleName = "style.data"
    var inputStream: InputStream? = null
    try {
        inputStream = this.assets.open(styleName)
        val b = ByteArray(inputStream.available())
        inputStream.read(b)
        return b
    } catch (e: IOException) {
        e.printStackTrace()
    } finally {
        try {
            inputStream?.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
    return null
}
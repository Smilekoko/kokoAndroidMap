package com.shiki.koko.map

import android.app.Application
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.os.Build
import com.amap.api.location.AMapLocationClient
import com.shiki.koko.base.exception.CrashActivityLifecycleCallbacks
import com.shiki.koko.base.exception.CrashHelper
import com.shiki.koko.base.sHA1


class MapApplication : Application() {


    override fun onCreate() {
        super.onCreate()

        CrashHelper(this).initCrashLogLocally()

        setAmapApiKey()

        //签名包含多个SHA1的,需要用对
        val sha1 = sHA1()
        println(sha1)
    }

    private fun setAmapApiKey() {

        val value = appInfo().metaData.getString(AMAP_APIKEY)

        /**
         * setApiKey必须在启动Activity或者Application的onCreate里进行， 在其他地方使用有可能无效,
         * 如果使用setApiKey设置key，则AndroidManifest.xml里的key会失效
         */
        AMapLocationClient.setApiKey(value)

    }


    private fun Application.appInfo(): ApplicationInfo {
        val appInfo = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            packageManager.getApplicationInfo(packageName, PackageManager.ApplicationInfoFlags.of(PackageManager.GET_META_DATA.toLong()))
        } else {
            packageManager.getApplicationInfo(packageName, PackageManager.GET_META_DATA)
        }
        return appInfo
    }
}
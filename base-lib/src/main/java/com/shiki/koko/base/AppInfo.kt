package com.shiki.koko.base

import android.app.Application
import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.os.Build
import androidx.core.content.pm.PackageInfoCompat

// 获取应用信息

//应用包信息
val Context.packageInfo: PackageInfo
    get() = packageManager.getPackageInfo(packageName, 0)

//应用版本信息
val Context.appVersionName: String
    get() = packageInfo.versionName

//应用版本号
val Context.appVersionCode: Long
    get() = PackageInfoCompat.getLongVersionCode(packageInfo)

//应用名
val Context.appName: String
    get() = applicationInfo.loadLabel(packageManager).toString()

//应用图标
val Context.appIcon: Drawable
    get() = packageInfo.applicationInfo.loadIcon(packageManager)


//应用Meta-Data信息
fun Context.appInfoMetaData(): ApplicationInfo {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        packageManager.getApplicationInfo(
            packageName,
            PackageManager.ApplicationInfoFlags.of(PackageManager.GET_META_DATA.toLong())
        )
    } else {
        packageManager.getApplicationInfo(packageName, PackageManager.GET_META_DATA)
    }
}


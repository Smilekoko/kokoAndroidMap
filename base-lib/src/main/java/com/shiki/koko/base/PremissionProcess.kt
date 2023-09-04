package com.shiki.koko.base

import android.app.Activity
import android.os.Build
import androidx.core.app.ActivityCompat

/**
 * 权限常规处理流程
 */
fun PermissionLauncher.permissionProcess(
    activity: Activity,
    permissions: Array<String>,
    agreeAction: (() -> Unit)? = null,
    denyAction: (() -> Unit)? = null,
    denyNotAskAction: (() -> Unit)? = null,
) {
    launch(permissions) { maps ->
        val denyPermissions = maps.filter { map -> !map.value }.keys
        if (denyPermissions.isEmpty()) {
            //同意全权限
            agreeAction?.invoke()
        } else {
            //应用的targetSDKVersion < 23时，权限检查仍是早期的形式（仅在安装时赋予权限，使用时将不被提醒）；
            //应用的targetSDKVersion ≥ 23时，则将使用新的运行时权限规则。
            if (Build.VERSION.SDK_INT >= 23) {
                //不再询问的权限
                val notAskPermission = ArrayList<String>()
                //拒绝的权限
                denyPermissions.forEach { p ->
                    //1.拒绝下次还可以请求该权限 true
                    //2.拒绝且不再询问 false
                    //3.安装后初始系统设置里禁止 false,但这种情况仍旧可以请求权限
                    val showUI = ActivityCompat.shouldShowRequestPermissionRationale(activity, p)
                    if (!showUI) {
                        notAskPermission.add(p)
                    }
                }
                if (notAskPermission.isNotEmpty()) {
                    denyNotAskAction?.invoke()
                } else {
                    denyAction?.invoke()
                }
            } else {
                denyAction?.invoke()
            }
        }
    }

}
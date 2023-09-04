package com.shiki.koko.base

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi

/**
 * 权限请求
 */
fun permissionsIntent(permissions: Array<String>): Intent {
    return Intent(ActivityResultContracts.RequestMultiplePermissions.ACTION_REQUEST_PERMISSIONS).putExtra(
        ActivityResultContracts.RequestMultiplePermissions.EXTRA_PERMISSIONS, permissions
    )
}

/**
 * 访问所有文件权限设置界面
 * https://blog.csdn.net/qq_17766199/article/details/115351949
 */
@RequiresApi(Build.VERSION_CODES.R)
fun settingManageExternalStorage(uri: Uri? = null): Intent {
    val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION, uri)
//    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    return intent
}
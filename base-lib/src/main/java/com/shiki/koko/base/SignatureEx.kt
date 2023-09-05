package com.shiki.koko.base

import android.R.attr
import android.content.Context
import android.content.pm.PackageManager
import java.security.MessageDigest


//签名文件方法


//代码段获取当前APK使用的签明文件SHA1。
fun Context.sHA1(): String? {
    try {
        val info = packageManager.getPackageInfo(
            packageName, PackageManager.GET_SIGNATURES
        );
        val cert = info.signatures[0].toByteArray()
        val md = MessageDigest.getInstance("SHA1")
        val publicKey = md.digest(cert)
        val hexString = StringBuffer()

        for (i in publicKey.indices) {

            val appendString = Integer.toHexString(0xFF and publicKey[i].toInt())
                .uppercase()
            if (appendString.length == 1) hexString.append("0")
            hexString.append(appendString)
            hexString.append(":")
        }
        val result = hexString.toString()
        return result.substring(0, result.length - 1)
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return null
}
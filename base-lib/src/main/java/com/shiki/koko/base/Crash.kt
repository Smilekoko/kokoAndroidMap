package com.shiki.koko.base

import android.app.Application
import android.os.Build
import com.shiki.koko.base.file.cacheDirPath
import com.shiki.koko.base.file.print
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.system.exitProcess

inline val sdkVersionName: String get() = Build.VERSION.RELEASE

inline val sdkVersionCode: Int get() = Build.VERSION.SDK_INT

inline val deviceManufacturer: String get() = Build.MANUFACTURER

inline val deviceModel: String get() = Build.MODEL


/**
 * 保存异常日志信息
 */
fun Application.initCrashLogLocally() =
    handleUncaughtException { thread, throwable ->
        val dirPath: String = applicationContext.cacheDirPath
        val dateTime = SimpleDateFormat("yyyy-MM-dd_HH:mm:ss", Locale.getDefault()).format(Date())
        val file = File(dirPath, "crash_$dateTime.txt")
        //主要写入信息
        val appVersionName = applicationContext.appVersionName
        val appVersionCode = applicationContext.appVersionCode
        val sdkVersionName = sdkVersionName
        val sdkVersionCode = sdkVersionCode
        val deviceManufacturer = deviceManufacturer
        val deviceModel = deviceModel
        //写入到文件
        file.print {
            println("Time:          $dateTime")
            println("App version:   $appVersionName ($appVersionCode)")
            println("OS version:    Android $sdkVersionName ($sdkVersionCode)")
            println("Manufacturer:  $deviceManufacturer")
            println("Model:         $deviceModel")
            println("Thread:        ${thread.name}")
            println()
            throwable.printStackTrace(this)
        }
    }

inline fun handleUncaughtException(crossinline block: (Thread, Throwable) -> Unit) {
    // 获取系统默认的UncaughtException处理器
    val defaultCrashHandler = Thread.getDefaultUncaughtExceptionHandler()
    // 设置该CrashHandler为程序的默认处理器
    Thread.setDefaultUncaughtExceptionHandler { thread, throwable ->

        //打印异常日日志
        block(thread, throwable)

        //系统默认的异常处理会导致崩溃
        defaultCrashHandler?.uncaughtException(thread, throwable)
    }
}

//程序退出
fun processExit() {
    try {
        // 如果处理了，让程序继续运行3秒再退出，保证文件保存并上传到服务器
        Thread.sleep(3000)
    } catch (e: InterruptedException) {
        e.printStackTrace()
    }
    android.os.Process.killProcess(android.os.Process.myPid())
    exitProcess(1)
}


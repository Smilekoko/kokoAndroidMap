package com.shiki.koko.base.exception

import android.app.Application
import android.os.Build
import android.util.Log
import com.shiki.koko.base.appVersionCode
import com.shiki.koko.base.appVersionName
import com.shiki.koko.base.file.cacheDirPath
import com.shiki.koko.base.file.print
import com.shiki.koko.base.toast
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
 * 异常捕获自己处理
 */
class CrashHelper(application: Application) {

    private val callbacks: CrashActivityLifecycleCallbacks = CrashActivityLifecycleCallbacks(application)
    private val context = application.applicationContext
    private val TAG = "CrashHelper"

    fun initCrashLogLocally() {
        Thread.setDefaultUncaughtExceptionHandler(UncaughtExceptionHandler())
    }

    /**
     * 写入崩溃日志到文件
     */
    fun saveCrashLog(thread: Thread, throwable: Throwable) {
        val dirPath: String = context.cacheDirPath
        val dateTime = SimpleDateFormat("yyyy-MM-dd_HH:mm:ss", Locale.getDefault()).format(Date())
        val file = File(dirPath, "crash_$dateTime.txt")
        Log.e(TAG, "崩溃日志 ${file.absolutePath}")
        //主要写入信息
        val appVersionName = context.appVersionName
        val appVersionCode = context.appVersionCode
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

    // 获取系统默认的UncaughtException处理器
    private val defaultCrashHandler = Thread.getDefaultUncaughtExceptionHandler()

    //自定义异常处理器
    private inner class UncaughtExceptionHandler : Thread.UncaughtExceptionHandler {
        override fun uncaughtException(t: Thread, e: Throwable) {
            //保存崩溃日志
            saveCrashLog(t, e)

            //程序退出
            processExit()
        }

    }

    //程序退出
    fun processExit() {
        try {
            context.toast("系统异常,3秒后退出")
            Thread.sleep(3000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        //退出所有活动
        callbacks.finishActivity()

        //退出程序
        android.os.Process.killProcess(android.os.Process.myPid())
        //System.exit()是Java虚拟机中用来退出虚拟机的方法，和Android程序无关，如
        //果在Android程序中使用，退出应用程序是没有问题的，但是退出来以后程序有可能会留下一堆问题。
        runCatching {
            exitProcess(0)
        }
    }

}
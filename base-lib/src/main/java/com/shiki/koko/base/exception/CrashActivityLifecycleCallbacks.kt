package com.shiki.koko.base.exception

import android.app.Activity
import android.app.Application
import android.os.Bundle

class CrashActivityLifecycleCallbacks(application: Application) : Application.ActivityLifecycleCallbacks {

    private val activityList = ArrayList<Activity>()

    init {
        application.registerActivityLifecycleCallbacks(this)
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        activityList.add(activity)
    }

    override fun onActivityStarted(activity: Activity) = Unit
    override fun onActivityResumed(activity: Activity) = Unit
    override fun onActivityPaused(activity: Activity) = Unit
    override fun onActivityStopped(activity: Activity) = Unit
    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) = Unit

    override fun onActivityDestroyed(activity: Activity) {
        activityList.remove(activity)
    }

    /**
     * 关闭所有Activity
     */
    fun finishActivity() {
        activityList.forEach { it.finish() }
    }
}
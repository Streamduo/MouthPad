package com.mouth.pad

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import com.mouth.pad.utils.Logger
import com.tencent.mmkv.MMKV
import kotlin.properties.Delegates

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
        Logger.i("1")
        registerActivityLifecycleCallbacks(mActivityLifecycleCallbacks)
        initMMKV()
    }

    private fun initMMKV() {
        // 设置初始化的根目录
        val rootDir = MMKV.initialize(getExternalFilesDir(null).toString() + "/mmkv")
        MMKV.initialize(rootDir)
    }


    private var onPauseName = ""
    private val mActivityLifecycleCallbacks = object : Application.ActivityLifecycleCallbacks {
        override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
//            Log.d(TAG, "onResumed==" + onPauseName + "onCreated==" + activity.javaClass.simpleName)
        }

        override fun onActivityStarted(activity: Activity) {
//            Log.d(TAG, "onStartActLog: " + activity.javaClass.simpleName)
        }

        override fun onActivityResumed(activity: Activity) {

        }

        override fun onActivityPaused(activity: Activity) {
            onPauseName = activity.javaClass.simpleName
        }

        override fun onActivityStopped(activity: Activity) {
//            Log.d(TAG, "onStoppedActLog: " + activity.javaClass.simpleName)
        }

        override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {

        }

        override fun onActivityDestroyed(activity: Activity) {
//            Log.d(TAG, "onDestroy: " + activity.javaClass.simpleName)
        }
    }



    companion object {
        private val TAG = "MyApplication"
        var sha1: String? = null
        var appContext: Context by Delegates.notNull()
            private set
    }
}
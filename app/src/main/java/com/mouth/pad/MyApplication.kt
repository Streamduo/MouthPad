package com.mouth.pad

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import com.mouth.pad.utils.Logger
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import com.tencent.mmkv.MMKV
import kotlin.properties.Delegates

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
        Logger.i("1")
        registerActivityLifecycleCallbacks(mActivityLifecycleCallbacks)
        initMMKV()
        initSmartRefresh()
    }

    private fun initMMKV() {
        // 设置初始化的根目录
        val rootDir = MMKV.initialize(getExternalFilesDir(null).toString() + "/mmkv")
        MMKV.initialize(rootDir)
    }

    private fun initSmartRefresh() {
        ClassicsFooter.REFRESH_FOOTER_LOADING = "加载中..."
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, _ ->
            ClassicsHeader(context) //.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
        }
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator { context, _ -> //指定为经典Footer，默认是 BallPulseFooter
            ClassicsFooter(context).setDrawableSize(20f)
        }
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
package com.mouth.pad.utils

import android.app.Activity
import com.mouth.pad.MainActivity
import java.util.*


/**
 *

 * @ClassName:      ActivityManager
 * @Description:     java类作用描述
 * @Author:         JiangMin
 * @CreateDate:     2021/3/2 17:15
 * @UpdateUser:     更新者
 * @UpdateDate:     2021/3/2 17:15
 * @UpdateRemark:   更新说明
 * @Version:        1.0
 */
class ActivityManager private constructor() {
    private val mActivityStack: Stack<Activity> = Stack()

    /**
     * activity入栈
     */
    fun addActivity(activity: Activity?) {
        mActivityStack.add(activity)
    }

    /**
     * activity出栈
     */
    fun removeActivity(activity: Activity?) {
        mActivityStack.remove(activity)
    }

    /**
     * 当栈的个数为1的时候，判断cls是否在栈内
     */
    fun currentActivity(cls: Class<*>): Boolean {
        if (mActivityStack.size != 1) {
            return true
        }
        for (activity in mActivityStack) {
            if (activity.javaClass == cls) {
                return true
            }
        }
        return false
    }

    fun getMainActivity(): MainActivity? {
        for (activity in mActivityStack) {
            if (activity.javaClass == MainActivity::class.java) {
                return activity as MainActivity
            }
        }
        return null
    }

    fun getLastActivityName(): String {
        if (mActivityStack.size < 2) {
            return ""
        }
        return mActivityStack[mActivityStack.size - 2].localClassName
    }

    //获取当前运行的activity
    fun getCurrentActivity() :Activity{
        return mActivityStack.lastElement()
    }


    companion object {
        private var instance: ActivityManager? = null
            get() {
                if (field == null) {
                    field = ActivityManager()
                }
                return field
            }

        @Synchronized
        fun get(): ActivityManager {
            return instance!!
        }
    }
}
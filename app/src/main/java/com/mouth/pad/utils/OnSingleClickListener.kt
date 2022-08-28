package com.bmncc.pis.ylct.utils

import android.view.View
import com.mouth.pad.utils.Logger

/**
 *

 * @ClassName:      OnSingleClickListener
 * @Description:    重写Click事件
 * @Author:         JiangMin
 * @CreateDate:     2020/12/12 10:32
 * @UpdateUser:     更新者
 * @UpdateDate:     2020/12/12 10:32
 * @UpdateRemark:   更新说明
 * @Version:        1.0
 */
abstract class OnSingleClickListener : View.OnClickListener {
    private val  MIN_CLICK_DELAY_TIME = 500
    private var lastClickTime = 0L
    override fun onClick(v: View?) {
        val currentTime = System.currentTimeMillis()
        if (currentTime - lastClickTime <= MIN_CLICK_DELAY_TIME) {
            Logger.e("重复点击")
            return
        } else {
            lastClickTime = currentTime
            onSingleClick(v)
        }
    }

    abstract fun onSingleClick(v: View?)
}

fun View.setOnSingleClickListener(l: (() -> Unit)?) {
    setOnClickListener(object : OnSingleClickListener() {
        override fun onSingleClick(v: View?) {
            l?.invoke()
        }
    })
}

fun View.setOnSingleClickListenerWithView(l: ((view: View?) -> Unit)?) {
    setOnClickListener(object : OnSingleClickListener() {
        override fun onSingleClick(v: View?) {
            l?.invoke(v)
        }
    })
}

fun View.setOnSingleClickListener(l: View.OnClickListener) {
    setOnClickListener(object : OnSingleClickListener() {
        override fun onSingleClick(v: View?) {
            l.onClick(v)
        }
    })
}
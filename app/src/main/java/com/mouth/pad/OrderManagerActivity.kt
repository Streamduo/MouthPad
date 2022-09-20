package com.mouth.pad

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bmncc.pis.ylct.utils.setOnSingleClickListener
import com.mouth.pad.base.BaseActivity
import kotlinx.android.synthetic.main.activity_order_manager.*
import kotlinx.android.synthetic.main.layout_title_subtitle.*

//订单管理
class OrderManagerActivity : BaseActivity() {

    override fun layoutId(): Int {
        return R.layout.activity_order_manager
    }

    override fun initData() {
    }

    override fun initView() {
        tv_title.text = "订单管理"
        title_back.setOnSingleClickListener {
            finish()
        }
        tv_subtitle.text = "跟踪管理"
        tv_subtitle.setOnSingleClickListener {
            TrackingManagementActivity.launchTrackingManagementActivity(this)
        }
        te_add.setOnSingleClickListener {

        }
        te_delete.setOnSingleClickListener {

        }
        //发送
        te_send.setOnSingleClickListener {

        }

    }

    override fun start() {
    }

    override fun isNeedLogin(): Boolean {
        return true
    }

    override fun isUseBlackFontWithStatusBar(): Boolean {
        return false
    }

    override fun getStatusBarColor(): Int {
        return R.color.color_1876FF
    }

    companion object {

        fun launchOrderManagerActivity(context: Context?) {
            context?.startActivity(Intent(context, OrderManagerActivity::class.java).apply {
            })
        }

    }

}
package com.mouth.pad

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bmncc.pis.ylct.utils.setOnSingleClickListener
import com.mouth.pad.base.BaseActivity
import kotlinx.android.synthetic.main.activity_tracking_management.*
import kotlinx.android.synthetic.main.layout_title.*

//订单全过程跟踪管理
class TrackingManagementActivity : BaseActivity() {

    override fun layoutId(): Int {
        return R.layout.activity_tracking_management
    }

    override fun initData() {
    }

    override fun initView() {
        tv_title.text = "订单全过程跟踪管理"
        title_back.setOnSingleClickListener {
            finish()
        }
        //查询
        te_query.setOnSingleClickListener {

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

        fun launchTrackingManagementActivity(context: Context?) {
            context?.startActivity(Intent(context, TrackingManagementActivity::class.java).apply {
            })
        }

    }

}
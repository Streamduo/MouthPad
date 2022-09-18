package com.mouth.pad

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bmncc.pis.ylct.utils.setOnSingleClickListener
import com.mouth.pad.base.BaseActivity
import kotlinx.android.synthetic.main.activity_consumption_query.*
import kotlinx.android.synthetic.main.layout_title.*

//消耗查询
class ConsumptionQueryActivity : BaseActivity() {

    override fun layoutId(): Int {
        return R.layout.activity_consumption_query
    }

    override fun initData() {
    }

    override fun initView() {

        tv_title.text = "消耗查询"
        title_back.setOnSingleClickListener {
            finish()
        }
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

        fun launchConsumptionQueryActivity(context: Context?) {
            context?.startActivity(Intent(context, ConsumptionQueryActivity::class.java).apply {
            })
        }

    }
}
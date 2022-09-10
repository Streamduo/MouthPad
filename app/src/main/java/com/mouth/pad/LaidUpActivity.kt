package com.mouth.pad

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bmncc.pis.ylct.utils.setOnSingleClickListener
import com.mouth.pad.base.BaseActivity
import kotlinx.android.synthetic.main.layout_title.*

class LaidUpActivity : BaseActivity() {

    override fun layoutId(): Int {
        return R.layout.activity_laid_up
    }

    override fun initData() {
    }

    override fun initView() {
        tv_title.text = "物资入库"
        title_back.setOnSingleClickListener {
            finish()
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

        fun launchLaidUpActivity(context: Context?) {
            context?.startActivity(Intent(context, LaidUpActivity::class.java).apply {
            })
        }

    }
}
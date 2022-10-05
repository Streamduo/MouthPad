package com.mouth.pad

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bmncc.pis.ylct.utils.setOnSingleClickListener
import com.mouth.pad.base.BaseActivity
import kotlinx.android.synthetic.main.activity_inventory_query.*
import kotlinx.android.synthetic.main.layout_title.*

//库存查询
class InventoryQueryActivity : BaseActivity() {

    override fun layoutId(): Int {
        return R.layout.activity_inventory_query
    }

    override fun initData() {
    }

    override fun initView() {
        tv_title.text = "库存查询"
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

        fun launchInventoryQueryActivity(context: Context?) {
            context?.startActivity(Intent(context, InventoryQueryActivity::class.java).apply {
            })
        }

    }

}
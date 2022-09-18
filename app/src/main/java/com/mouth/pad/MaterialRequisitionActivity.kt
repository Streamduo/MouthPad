package com.mouth.pad

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bmncc.pis.ylct.utils.setOnSingleClickListener
import com.mouth.pad.base.BaseActivity
import kotlinx.android.synthetic.main.activity_material_requisition.*
import kotlinx.android.synthetic.main.layout_title_subtitle.*

//物资请领
class MaterialRequisitionActivity : BaseActivity() {

    override fun layoutId(): Int {
        return R.layout.activity_material_requisition
    }

    override fun initData() {
    }

    override fun initView() {
        tv_title.text = "物资请领"
        tv_subtitle.text = "记录查询"
        title_back.setOnSingleClickListener {
            finish()
        }
        tv_subtitle.setOnSingleClickListener {
            MaterialRequisitionRecordActivity.launchMaterialRequisitionRecordActivity(this)
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

        fun launchMaterialRequisitionActivity(context: Context?) {
            context?.startActivity(Intent(context, MaterialRequisitionActivity::class.java).apply {
            })
        }

    }

}
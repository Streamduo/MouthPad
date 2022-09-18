package com.mouth.pad

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bmncc.pis.ylct.utils.setOnSingleClickListener
import com.mouth.pad.base.BaseActivity
import kotlinx.android.synthetic.main.activity_material_requisition_record.*
import kotlinx.android.synthetic.main.layout_title.*
import kotlinx.android.synthetic.main.layout_title.title_back
import kotlinx.android.synthetic.main.layout_title.tv_title
import kotlinx.android.synthetic.main.layout_title_subtitle.*

//物资请领记录查询
class MaterialRequisitionRecordActivity : BaseActivity() {

    override fun layoutId(): Int {
        return R.layout.activity_material_requisition_record
    }

    override fun initData() {
    }

    override fun initView() {
        tv_title.text = "物资请领记录"
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

        fun launchMaterialRequisitionRecordActivity(context: Context?) {
            context?.startActivity(Intent(context, MaterialRequisitionRecordActivity::class.java).apply {
            })
        }

    }

}
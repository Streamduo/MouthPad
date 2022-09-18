package com.mouth.pad

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bmncc.pis.ylct.utils.setOnSingleClickListener
import com.mouth.pad.base.BaseActivity
import com.permissionx.guolindev.PermissionX
import com.xys.libzxing.zxing.activity.CaptureActivity
import kotlinx.android.synthetic.main.activity_laid_up.*
import kotlinx.android.synthetic.main.layout_title.*

//物资入库
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

        te_add.setOnSingleClickListener {
            getPermissions()

        }
        te_delete.setOnSingleClickListener {

        }
        //审核
        te_verify.setOnSingleClickListener {

        }
        //保存
        te_save.setOnSingleClickListener {

        }
    }

    private fun getPermissions() {
        PermissionX.init(this)
            .permissions(
                Manifest.permission.CAMERA
            )
            .request { allGranted, _, _ ->
                //所有权限都允许
                if (allGranted) {
                    goCapture()
                }
            }
    }

    private fun goCapture() {
        startActivityForResult(Intent(this, CaptureActivity::class.java), 10)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 10 && resultCode == RESULT_OK) {
            val bundle = data?.extras
            if (bundle != null) {
                val result = bundle.getString("result")
                showLongToast(result)
            }
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
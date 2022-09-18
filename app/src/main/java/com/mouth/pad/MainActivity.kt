package com.mouth.pad

import android.view.View
import com.bmncc.pis.ylct.utils.setOnSingleClickListener
import com.mouth.pad.base.BaseActivity
import com.mouth.pad.bean.LoginUserBean
import com.mouth.pad.utils.Const
import com.mouth.pad.utils.SpUtil
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_title.*

class MainActivity : BaseActivity() {

    override fun layoutId(): Int {
        return R.layout.activity_main
    }

    override fun initData() {
    }

    override fun onResume() {
        super.onResume()
        val loginUserBean = SpUtil.decodeParcelable(Const.LOGIN_USER_BEAN, LoginUserBean::class.java)
        setUserInfo(loginUserBean)
    }

    override fun initView() {
        //登录
        te_user_name.setOnSingleClickListener {
            if (!LoginActivity.isValidity()){
                LoginActivity.toLogin(this)
            }
        }
        //物资入库
        te_laid_up?.setOnSingleClickListener {
           LaidUpActivity.launchLaidUpActivity(this)
        }
        //库存查询
        te_inventory_query?.setOnSingleClickListener {
           InventoryQueryActivity.launchInventoryQueryActivity(this)
        }
        //物资请领
        te_material_requisition?.setOnSingleClickListener {
           MaterialRequisitionActivity.launchMaterialRequisitionActivity(this)
        }
        //消耗查询
        te_consumption_query?.setOnSingleClickListener {
           ConsumptionQueryActivity.launchConsumptionQueryActivity(this)
        }
        //订单管理
        te_order_manager?.setOnSingleClickListener {
           OrderManagerActivity.launchOrderManagerActivity(this)
        }
        te_login_out?.setOnSingleClickListener {
            SpUtil.clearAll()
            te_user_name.text = getString(R.string.title_login)
            te_login_out.visibility = View.GONE
            showLongToast("退出登录成功")
        }
    }

    private fun setUserInfo(loginUserBean: LoginUserBean?){
        loginUserBean?.apply {
            te_user_name.text = nickname
        }
        if (LoginActivity.isValidity()){
            te_login_out.visibility = View.VISIBLE
        }else{
            te_login_out.visibility = View.GONE
        }
    }

    override fun start() {
    }

    override fun isNeedLogin(): Boolean {
        return false
    }

    override fun isUseBlackFontWithStatusBar(): Boolean {
        return false
    }

    override fun getStatusBarColor(): Int {
        return R.color.color_1876FF
    }

}
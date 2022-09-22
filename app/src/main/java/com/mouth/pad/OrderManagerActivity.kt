package com.mouth.pad

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.AdapterView
import androidx.recyclerview.widget.LinearLayoutManager
import com.bmncc.pis.ylct.utils.setOnSingleClickListener
import com.google.gson.Gson
import com.mouth.pad.adapter.StuffListAdapter
import com.mouth.pad.base.BaseActivity
import com.mouth.pad.bean.LoginUserBean
import com.mouth.pad.bean.TOrder
import com.mouth.pad.utils.Const
import com.mouth.pad.utils.Logger
import com.mouth.pad.utils.SpUtil
import com.xys.libzxing.zxing.activity.CaptureActivity
import kotlinx.android.synthetic.main.activity_order_manager.*
import kotlinx.android.synthetic.main.layout_title_subtitle.*
import java.util.*

//订单管理
class OrderManagerActivity : BaseActivity() {

    //    private var pvTime: TimePickerView? = null
//    private val onTimeSelectListener = OnTimeSelectListener { date, v ->
//        date?.let {
//            te_claim_date.text = TimeUtils.getYear(it)
//        }
//        pvTime?.dismiss()
//    }
    private val allNetViewModel by lazy {
        AllNetViewModel()
    }
    private val orderListAdapter by lazy {
        StuffListAdapter(R.layout.item_stuff_list)
    }
    private var selectDeptCode = ""
    private var selectDeptName = ""
    private var loginUserBean: LoginUserBean? = null

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

        loginUserBean = SpUtil.decodeParcelable(Const.LOGIN_USER_BEAN, LoginUserBean::class.java)
        ed_claimant.setText(loginUserBean?.nickname)

        rv_order_list?.apply {
            layoutManager = LinearLayoutManager(this@OrderManagerActivity)
            adapter = orderListAdapter
        }
        val departmentCode = resources.getStringArray(R.array.departmentCode)
        val department = resources.getStringArray(R.array.department)

        sp_department.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                selectDeptName = department[p2]
                selectDeptCode = departmentCode[p2]
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }
//        val type = booleanArrayOf(true, true, true, true, true, false)
//        //时间选择器
//        val pvTime = TimePickerBuilder(this, onTimeSelectListener)
//            .setType(type)
//            .build()

        te_add.setOnSingleClickListener {
            val text = te_add.text
            if (text == "添加") {
                te_add.text = "扫码"
                te_head.visibility = View.VISIBLE
                rl_haed.visibility = View.VISIBLE
            } else {
                goCapture()
            }
        }
        te_query.setOnSingleClickListener {
            VerticalListActivity.launchVerticalListActivity(this,0)
        }
        te_delete.setOnSingleClickListener {
            VerticalListActivity.launchVerticalListActivity(this,1)
        }
        //发送
        te_send.setOnSingleClickListener {
            val data = orderListAdapter.data
            val buyer = ed_claimant.text.toString()
            val supplier = ed_delivery_unit.text.toString()
            if (selectDeptCode.isEmpty()) {
                showToast("请选择科室")
                return@setOnSingleClickListener
            }
            if (supplier.isEmpty()) {
                showToast("请输入供货单位")
                return@setOnSingleClickListener
            }
            if (buyer.isEmpty()) {
                showToast("请输入采购员")
                return@setOnSingleClickListener
            }
            if (data.isNullOrEmpty()) {
                showToast("请添加商品")
                return@setOnSingleClickListener
            }
            val tOrder = TOrder(selectDeptCode, selectDeptName, buyer, supplier, data)
            val gson = Gson()
            val orderInfo = gson.toJson(tOrder)
            Logger.d(orderInfo)
            insertOrder(orderInfo)
        }

//        //选择申领日期
//        te_claim_date.setOnSingleClickListener {
//            pvTime?.show()
//        }

    }

    private fun insertOrder(orderInfo: String) {
        allNetViewModel.insertOrder(orderInfo).observe(this, {
            if (it.isOk()) {
                showToast("订单提交成功")
                te_add.text = "添加"
                selectDeptCode = ""
                selectDeptName = ""
                sp_department.setSelection(0)
                ed_delivery_unit.setText("")
                ed_claimant.setText(loginUserBean?.nickname)
                te_head.visibility = View.GONE
                rl_haed.visibility = View.GONE
                te_search_result.visibility = View.GONE
                rv_order_list.visibility = View.GONE
                orderListAdapter.data.clear()
                orderListAdapter.notifyDataSetChanged()
            }
        })
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
                selectByMaterialCode(result)
            }
        }
    }

    ////根据材料编号查询-材料基本信息
    private fun selectByMaterialCode(materialCode: String?) {
        allNetViewModel.selectByMaterialCode(materialCode).observe(this, {
            if (it.isOk()) {
                it.data?.apply{
                    te_search_result.visibility = View.VISIBLE
                    orderListAdapter.addData(this)
                }
            } else {
                it.msg?.let { msg ->
                    showToast(msg)
                }
            }
        })
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
package com.mouth.pad

import android.Manifest
import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.AdapterView
import androidx.recyclerview.widget.LinearLayoutManager
import com.bmncc.pis.ylct.utils.setOnSingleClickListener
import com.google.gson.Gson
import com.jeremyliao.liveeventbus.LiveEventBus
import com.mouth.pad.adapter.OrderStuffListAdapter
import com.mouth.pad.base.BaseActivity
import com.mouth.pad.bean.LoginUserBean
import com.mouth.pad.bean.TOrder
import com.mouth.pad.bean.TOrderDetail
import com.mouth.pad.bean.event.SelectStuffEvent
import com.mouth.pad.utils.Const
import com.mouth.pad.utils.Logger
import com.mouth.pad.utils.SpUtil
import com.mouth.pad.view.CurrencySingleDialog
import com.permissionx.guolindev.PermissionX
import com.xys.libzxing.zxing.activity.CaptureActivity
import kotlinx.android.synthetic.main.activity_order_manager.*
import kotlinx.android.synthetic.main.layout_title_subtitle.*
import java.util.*

//订单管理
class OrderManagerActivity : BaseActivity() {

    private val allNetViewModel by lazy {
        AllNetViewModel()
    }
    private val orderListAdapter by lazy {
        OrderStuffListAdapter(R.layout.item_stuff_list)
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
        tv_subtitle.text = "订单查询"
//        tv_subtitle.setOnSingleClickListener {
//            TrackingManagementActivity.launchTrackingManagementActivity(this)
//        }

        loginUserBean = SpUtil.decodeParcelable(Const.LOGIN_USER_BEAN, LoginUserBean::class.java)
        ed_claimant.setText(loginUserBean?.userName)

        rv_order_list?.apply {
            layoutManager = LinearLayoutManager(this@OrderManagerActivity)
            adapter = orderListAdapter
        }
        orderListAdapter.addChildClickViewIds(R.id.iv_delete)
        orderListAdapter.setOnItemChildClickListener { _, view, position ->
            when (view.id) {
                R.id.iv_delete -> {
                    orderListAdapter.removeAt(position)
                }
            }

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

        te_add.setOnSingleClickListener {
            te_head.visibility = View.VISIBLE
            rl_haed.visibility = View.VISIBLE
            te_search_result.visibility = View.VISIBLE
            te_select_stuff.visibility = View.VISIBLE
            te_send.visibility = View.VISIBLE
            rv_order_list.visibility = View.VISIBLE
        }
        tv_subtitle.setOnSingleClickListener {
            QueryOrderListActivity.launchQueryOrderListActivity(this)
        }
        te_select_stuff.setOnSingleClickListener {
            SelectStuffActivity.launchSelectStuffActivity(this)
        }
        LiveEventBus.get("SelectStuffEvent",SelectStuffEvent::class.java).observe(this,{
            val tMaterial = it.tMaterial
            tMaterial?.let { it1 -> orderListAdapter.addData(it1) }
        })
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
            val orderDetailList: MutableList<TOrderDetail> = ArrayList()

            for (tMaterial in data) {
                tMaterial.apply {
                    val tOrderDetail = TOrderDetail(
                        id,
                        invCode,
                        invName,
                        invModel,
                        planPrice,
                        unitName,
                        stuffNum.toString(),
                        balanceAmount,
                        noWarehousingNum.toString()
                    )
                    orderDetailList.add(tOrderDetail)
                }
            }
            val tOrder = TOrder(
                loginUserBean?.userName,
                selectDeptCode,
                selectDeptName,
                buyer,
                supplier,
                orderDetailList
            )
            val gson = Gson()
            val orderInfo = gson.toJson(tOrder)
            Logger.d(orderInfo)
            insertOrder(orderInfo)
        }
    }

    private fun insertOrder(orderInfo: String) {
        allNetViewModel.insertOrder(orderInfo).observe(this, {
            if (it.isOk()) {
                showToast("订单提交成功")
                sp_department.setSelection(0)
                ed_delivery_unit.setText("")
                ed_claimant.setText(loginUserBean?.userName)
                te_head.visibility = View.GONE
                rl_haed.visibility = View.GONE
                te_send.visibility = View.GONE
                te_select_stuff.visibility = View.GONE
                te_search_result.visibility = View.GONE
                rv_order_list.visibility = View.GONE
                orderListAdapter.data.clear()
                orderListAdapter.notifyDataSetChanged()
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
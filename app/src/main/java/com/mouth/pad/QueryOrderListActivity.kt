package com.mouth.pad

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.recyclerview.widget.LinearLayoutManager
import com.bigkoo.pickerview.builder.TimePickerBuilder
import com.bigkoo.pickerview.listener.OnTimeSelectListener
import com.bigkoo.pickerview.view.TimePickerView
import com.bmncc.pis.ylct.utils.TimeUtils
import com.bmncc.pis.ylct.utils.setOnSingleClickListener
import com.mouth.pad.adapter.OrderQueryListAdapter
import com.mouth.pad.base.BaseActivity
import com.mouth.pad.bean.LoginUserBean
import com.mouth.pad.utils.Const
import com.mouth.pad.utils.Const.PAGE_SIZE
import com.mouth.pad.utils.SpUtil
import com.mouth.pad.view.CurrencyDialog
import kotlinx.android.synthetic.main.activity_query_material_list.*
import kotlinx.android.synthetic.main.activity_query_order_list.*
import kotlinx.android.synthetic.main.activity_query_order_list.rv_refresh_list
import kotlinx.android.synthetic.main.activity_query_order_list.sp_department
import kotlinx.android.synthetic.main.activity_query_order_list.sp_isApproval
import kotlinx.android.synthetic.main.activity_query_order_list.srl_refresh
import kotlinx.android.synthetic.main.activity_query_order_list.te_query
import kotlinx.android.synthetic.main.activity_query_order_list.te_reset
import kotlinx.android.synthetic.main.activity_query_order_list.te_search_result
import kotlinx.android.synthetic.main.layout_title_subtitle.*

//订单查询
class QueryOrderListActivity : BaseActivity() {

    var currentIndex = 1
    private val pageSize = PAGE_SIZE
    private val orderQueryListAdapter by lazy {
        OrderQueryListAdapter(R.layout.item_query_order_list)
    }
    private var selectDeptCode = ""
    private var selectDeptName = ""
    private var selectIsApprovalCode = ""
    private var selectIsApprovalName = ""
    private var pvTime: TimePickerView? = null
    private val onTimeSelectListener = OnTimeSelectListener { date, v ->
        date?.let {
            te_order_date.text = TimeUtils.getYearDay(it)
        }
        pvTime?.dismiss()
    }
    private val allNetViewModel by lazy {
        AllNetViewModel()
    }
    private var loginUserBean: LoginUserBean? = null

    override fun layoutId(): Int {
        return R.layout.activity_query_order_list
    }

    override fun initData() {
    }

    override fun initView() {
        tv_title.text = "订单查询"
        title_back.setOnSingleClickListener {
            finish()
        }
        loginUserBean = SpUtil.decodeParcelable(Const.LOGIN_USER_BEAN, LoginUserBean::class.java)
        ed_buyer.setText(loginUserBean?.userName)
        loginUserBean?.userName?.length?.let { ed_buyer.setSelection(it) }
        rv_refresh_list.apply {
            layoutManager = LinearLayoutManager(this@QueryOrderListActivity)
            adapter = orderQueryListAdapter
        }
        srl_refresh?.apply {
            //是否启用下拉刷新功能
            setEnableRefresh(true)
            //是否启用上拉加载功能
            setEnableLoadMore(true)
            //是否启用列表惯性滑动到底部时自动加载更多
            setEnableAutoLoadMore(true)
            //是否在列表不满一页时候开启上拉加载功能
            setEnableLoadMoreWhenContentNotFull(true)
            setOnRefreshListener {
                currentIndex = 1
                getQueryDate()
            }
            setOnLoadMoreListener {
                currentIndex++
                getQueryDate()
            }
        }
        val departmentCode = resources.getStringArray(R.array.departmentCodeAll)
        val department = resources.getStringArray(R.array.departmentAll)

        sp_department.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                selectDeptName = department[p2]
                selectDeptCode = departmentCode[p2]
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }
        val type = booleanArrayOf(true, true, true, false, false, false)
        //时间选择器
        pvTime = TimePickerBuilder(this, onTimeSelectListener)
            .setType(type)
            .build()
        te_order_date.setOnSingleClickListener {
            pvTime?.show()
        }
        val isApprovalCode = resources.getStringArray(R.array.isApprovalCode)
        val isApproval = resources.getStringArray(R.array.isApproval)

        sp_isApproval.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                selectIsApprovalName = isApproval[p2]
                selectIsApprovalCode = isApprovalCode[p2]
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }
        //重置
        te_reset.setOnSingleClickListener {
            te_search_result.visibility = View.GONE
            orderQueryListAdapter.data.clear()
            orderQueryListAdapter.notifyDataSetChanged()
            ed_buyer.setText(loginUserBean?.userName)
            sp_department.setSelection(0)
            sp_isApproval.setSelection(0)
            te_order_date.text = ""
        }
        //查询
        te_query.setOnSingleClickListener {
            currentIndex = 1
            getQueryDate()
        }
        orderQueryListAdapter.addChildClickViewIds(R.id.te_delete, R.id.te_check)
        orderQueryListAdapter.setOnItemChildClickListener { _, view, position ->
            val item = orderQueryListAdapter.getItem(position)
            when (view.id) {
                R.id.te_delete -> {
                    val currencyDialog = CurrencyDialog()
                    currencyDialog.showDialog(this, "是否删除订单？", "取消", "确定")
                    currencyDialog.setDialogClickListener(object :
                        CurrencyDialog.DialogClickListener {
                        override fun onLeftClick() {
                            currencyDialog.dialog.dismiss()
                        }

                        override fun onRightClick() {
                            currencyDialog.dialog.dismiss()
                            deleteOrder(item.id, position)
                        }

                    })
                }
                R.id.te_check -> {
                    val currencyDialog = CurrencyDialog()
                    currencyDialog.showDialog(this, "是否审核？", "取消", "同意")
                    currencyDialog.setDialogClickListener(object :
                        CurrencyDialog.DialogClickListener {
                        override fun onLeftClick() {
                            currencyDialog.dialog.dismiss()
                        }

                        override fun onRightClick() {
                            currencyDialog.dialog.dismiss()
                            approvalOrder(item.id, position, loginUserBean?.userName)
                        }

                    })

                }
            }
        }
    }

    private fun getQueryDate() {
        val buyer = ed_buyer.text.toString()
        val orderDate = te_order_date.text.toString()
        queryAllOrder(
            buyer, selectDeptCode, orderDate,
            selectIsApprovalCode, currentIndex, pageSize
        )
    }

    private fun queryAllOrder(
        buyer: String,
        deptCode: String,
        orderDate: String,
        isApproval: String,
        pageNum: Int,
        pageSize: Int
    ) {
        allNetViewModel.getAllPageOrder(
            buyer, deptCode, orderDate, isApproval,
            pageNum, pageSize
        ).observe(this, {
            if (it.isOk()) {
                if (!it.data?.rows.isNullOrEmpty()) {
                    te_search_result.visibility = View.VISIBLE
                    if (currentIndex == 1) {
                        orderQueryListAdapter.setNewInstance(it.data?.rows)
                    } else {
                        orderQueryListAdapter.addData(it.data?.rows!!)
                    }
                } else {
                    if (currentIndex == 1) {
                        te_search_result.visibility = View.GONE
                        showToast("暂无相关记录")
                        orderQueryListAdapter.data.clear()
                        orderQueryListAdapter.notifyDataSetChanged()
                    } else {
                        showToast("没有更多了")
                    }
                }
            } else {
                it.msg?.let { msg ->
                    showToast(msg)
                }
            }
            if (currentIndex == 1) {
                srl_refresh.finishRefresh()
            } else {
                srl_refresh.finishLoadMore()
            }
        })
    }

    //删除订单
    private fun deleteOrder(orderId: String?, position: Int) {
        allNetViewModel.deleteOrder(orderId).observe(this, {
            if (it.isOk()) {
                orderQueryListAdapter.removeAt(position)
                showToast("删除成功")
            } else {
                it.msg?.let { msg ->
                    showToast(msg)
                }
            }
        })
    }

    //审核订单
    private fun approvalOrder(orderId: String?, position: Int, nickname: String?) {
        allNetViewModel.approvalOrder(orderId, nickname).observe(this, {
            if (it.isOk()) {
                orderQueryListAdapter.removeAt(position)
                showToast("审核成功")
            } else {
                it.msg?.let { msg ->
                    showToast(msg)
                }
            }
        })
    }

    override fun start() {
    }

    override fun isUseBlackFontWithStatusBar(): Boolean {
        return false
    }

    override fun getStatusBarColor(): Int {
        return R.color.color_1876FF
    }

    companion object {

        fun launchQueryOrderListActivity(context: Context?) {
            context?.startActivity(Intent(context, QueryOrderListActivity::class.java).apply {
            })
        }

    }

}
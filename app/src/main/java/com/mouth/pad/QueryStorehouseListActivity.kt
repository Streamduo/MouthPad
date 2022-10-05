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
import com.mouth.pad.adapter.WarehouseQueryListAdapter
import com.mouth.pad.base.BaseActivity
import com.mouth.pad.bean.LoginUserBean
import com.mouth.pad.utils.Const
import com.mouth.pad.utils.Const.PAGE_SIZE
import com.mouth.pad.utils.SpUtil
import com.mouth.pad.view.CurrencyDialog
import kotlinx.android.synthetic.main.activity_query_order_list.*
import kotlinx.android.synthetic.main.activity_query_storehouse_list.*
import kotlinx.android.synthetic.main.activity_query_storehouse_list.rv_refresh_list
import kotlinx.android.synthetic.main.activity_query_storehouse_list.sp_business_type
import kotlinx.android.synthetic.main.activity_query_storehouse_list.sp_department
import kotlinx.android.synthetic.main.activity_query_storehouse_list.sp_isApproval
import kotlinx.android.synthetic.main.activity_query_storehouse_list.sp_storehouse
import kotlinx.android.synthetic.main.activity_query_storehouse_list.srl_refresh
import kotlinx.android.synthetic.main.activity_query_storehouse_list.te_query
import kotlinx.android.synthetic.main.activity_query_storehouse_list.te_reset
import kotlinx.android.synthetic.main.activity_query_storehouse_list.te_search_result
import kotlinx.android.synthetic.main.layout_title_subtitle.*

//入库信息查询
class QueryStorehouseListActivity : BaseActivity() {

    var currentIndex = 1
    private val pageSize = PAGE_SIZE
    private val warehouseQueryListAdapter by lazy {
        WarehouseQueryListAdapter(R.layout.item_query_warehouse_list)
    }
    private var selectDeptCode = ""
    private var selectDeptName = ""
    private var selectIsApprovalCode = ""
    private var selectIsApprovalName = ""
    private var selectStorehouseCode = ""
    private var selectStorehouse = ""
    private var selectBusinessTypeCode = ""
    private var selectBusinessType = ""

    private val allNetViewModel by lazy {
        AllNetViewModel()
    }
    private var loginUserBean: LoginUserBean? = null

    override fun layoutId(): Int {
        return R.layout.activity_query_storehouse_list
    }

    override fun initData() {
    }

    override fun initView() {
        tv_title.text = "入库信息查询"
        title_back.setOnSingleClickListener {
            finish()
        }
        loginUserBean = SpUtil.decodeParcelable(Const.LOGIN_USER_BEAN, LoginUserBean::class.java)
        ed_creater.setText(loginUserBean?.nickname)
        loginUserBean?.nickname?.length?.let { ed_creater.setSelection(it) }
        rv_refresh_list.apply {
            layoutManager = LinearLayoutManager(this@QueryStorehouseListActivity)
            adapter = warehouseQueryListAdapter
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

        val businessTypeCode = resources.getStringArray(R.array.businessTypeCodeAll)
        val businessType = resources.getStringArray(R.array.businessTypeAll)

        sp_business_type.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                selectBusinessType = businessType[p2]
                selectBusinessTypeCode = businessTypeCode[p2]
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }

        val storehouseCode = resources.getStringArray(R.array.storehouseCodeAll)
        val storehouse = resources.getStringArray(R.array.storehouseAll)
        sp_storehouse.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                selectStorehouseCode = storehouseCode[p2]
                selectStorehouse = storehouse[p2]
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

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
            warehouseQueryListAdapter.data.clear()
            warehouseQueryListAdapter.notifyDataSetChanged()
            ed_creater.setText(loginUserBean?.nickname)
            sp_department.setSelection(0)
            sp_business_type.setSelection(0)
            sp_isApproval.setSelection(0)
        }
        //查询
        te_query.setOnSingleClickListener {
            currentIndex = 1
            getQueryDate()
        }

        warehouseQueryListAdapter.addChildClickViewIds(R.id.te_delete, R.id.te_check)
        warehouseQueryListAdapter.setOnItemChildClickListener { _, view, position ->
            val item = warehouseQueryListAdapter.getItem(position)
            when (view.id) {
                R.id.te_delete -> {
                    val currencyDialog = CurrencyDialog()
                    currencyDialog.showDialog(this, "是否删除入库信息？", "取消", "确定")
                    currencyDialog.setDialogClickListener(object :
                        CurrencyDialog.DialogClickListener {
                        override fun onLeftClick() {
                            currencyDialog.dialog.dismiss()
                        }

                        override fun onRightClick() {
                            currencyDialog.dialog.dismiss()
                            deleteStorehouse(item.id, position)
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
                            approvalStorehouse(item.id, position, loginUserBean?.nickname)
                        }

                    })

                }
            }
        }
    }

    private fun getQueryDate() {
        if (selectBusinessType == "全部类型") {
            selectBusinessType = ""
        }
        val creater = ed_creater.text.toString()
        getAllPageStorehouse(
            creater, selectBusinessType, selectDeptCode, selectStorehouseCode,
            selectIsApprovalCode, currentIndex, pageSize
        )
    }

    private fun getAllPageStorehouse(
        applicant: String,
        businessType: String,
        deptCode: String,
        warehouseCode: String,
        isApproval: String,
        pageNum: Int,
        pageSize: Int
    ) {
        allNetViewModel.getAllPageStorehouse(
            applicant, businessType, deptCode, warehouseCode, isApproval,
            pageNum, pageSize
        ).observe(this, {
            if (it.isOk()) {
                if (!it.data?.rows.isNullOrEmpty()) {
                    te_search_result.visibility = View.VISIBLE
                    if (currentIndex == 1) {
                        warehouseQueryListAdapter.setNewInstance(it.data?.rows)
                    } else {
                        warehouseQueryListAdapter.addData(it.data?.rows!!)
                    }
                } else {
                    if (currentIndex == 1) {
                        showToast("暂无相关记录")
                        warehouseQueryListAdapter.data.clear()
                        warehouseQueryListAdapter.notifyDataSetChanged()
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

    //删除入库
    private fun deleteStorehouse(orderId: String?, position: Int) {
        allNetViewModel.deleteStorehouse(orderId).observe(this, {
            if (it.isOk()) {
                warehouseQueryListAdapter.removeAt(position)
                showToast("删除成功")
            } else {
                it.msg?.let { msg ->
                    showToast(msg)
                }
            }
        })
    }

    //审核入库
    private fun approvalStorehouse(orderId: String?, position: Int, nickname: String?) {
        allNetViewModel.approvalStorehouse(orderId, nickname).observe(this, {
            if (it.isOk()) {
                warehouseQueryListAdapter.removeAt(position)
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

        fun launchQueryStorehouseListActivity(context: Context?) {
            context?.startActivity(Intent(context, QueryStorehouseListActivity::class.java).apply {
            })
        }

    }

}
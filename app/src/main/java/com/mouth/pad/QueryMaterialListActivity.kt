package com.mouth.pad

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.AdapterView
import androidx.recyclerview.widget.LinearLayoutManager
import com.bigkoo.pickerview.builder.TimePickerBuilder
import com.bigkoo.pickerview.listener.OnTimeSelectListener
import com.bigkoo.pickerview.view.TimePickerView
import com.bmncc.pis.ylct.utils.TimeUtils
import com.bmncc.pis.ylct.utils.setOnSingleClickListener
import com.mouth.pad.adapter.MaterialRequisitionQueryListAdapter
import com.mouth.pad.base.BaseActivity
import com.mouth.pad.bean.LoginUserBean
import com.mouth.pad.utils.Const
import com.mouth.pad.utils.Const.PAGE_SIZE
import com.mouth.pad.utils.SpUtil
import com.mouth.pad.view.CurrencyDialog
import kotlinx.android.synthetic.main.activity_query_material_list.*
import kotlinx.android.synthetic.main.layout_title_subtitle.*

//物资请领信息查询
class QueryMaterialListActivity : BaseActivity() {

    var currentIndex = 1
    private val pageSize = PAGE_SIZE
    private val materialRequisitionQueryListAdapter by lazy {
        MaterialRequisitionQueryListAdapter(R.layout.item_query_material_requisition_list)
    }
    private var selectDeptCode = ""
    private var selectDeptName = ""
    private var selectIsApprovalCode = ""
    private var selectIsApprovalName = ""
    private var selectStorehouseCode = ""
    private var selectStorehouse = ""
    private var selectIsConsumeCode = ""
    private var selectIsConsume = ""
    private var pvTime: TimePickerView? = null
    private val onTimeSelectListener = OnTimeSelectListener { date, v ->
        date?.let {
            te_material_date.text = TimeUtils.getYearDay(it)
        }
        pvTime?.dismiss()
    }

    private val allNetViewModel by lazy {
        AllNetViewModel()
    }
    private var loginUserBean: LoginUserBean? = null

    override fun layoutId(): Int {
        return R.layout.activity_query_material_list
    }

    override fun initData() {
    }

    override fun initView() {
        tv_title.text = "物资请领查询"
        title_back.setOnSingleClickListener {
            finish()
        }
        loginUserBean = SpUtil.decodeParcelable(Const.LOGIN_USER_BEAN, LoginUserBean::class.java)
        ed_creater.setText(loginUserBean?.nickname)
        loginUserBean?.nickname?.length?.let { ed_creater.setSelection(it) }
        rv_refresh_list.apply {
            layoutManager = LinearLayoutManager(this@QueryMaterialListActivity)
            adapter = materialRequisitionQueryListAdapter
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

        val isConsumeCode = resources.getStringArray(R.array.isApprovalCode)
        val isConsume = resources.getStringArray(R.array.isApproval)

        sp_isConsume.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                selectIsConsume = isConsume[p2]
                selectIsConsumeCode = isConsumeCode[p2]
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }
        val type = booleanArrayOf(true, true, true, false, false, false)
        //时间选择器
        pvTime = TimePickerBuilder(this, onTimeSelectListener)
            .setType(type)
            .build()
        te_material_date.setOnSingleClickListener {
            pvTime?.show()
        }
        //重置
        te_reset.setOnSingleClickListener {
            materialRequisitionQueryListAdapter.data.clear()
            materialRequisitionQueryListAdapter.notifyDataSetChanged()
            ed_creater.setText(loginUserBean?.nickname)
            te_material_date.text = ""
            sp_department.setSelection(0)
            sp_isConsume.setSelection(0)
            sp_isApproval.setSelection(0)
            sp_storehouse.setSelection(0)
        }
        //查询
        te_query.setOnSingleClickListener {
            currentIndex = 1
            getQueryDate()
        }

        materialRequisitionQueryListAdapter.setOnItemChildClickListener { _, view, position ->
            val item = materialRequisitionQueryListAdapter.getItem(position)
            when (view.id) {
                R.id.te_delete -> {
                    val currencyDialog = CurrencyDialog()
                    currencyDialog.showDialog(this, "是否删除物资请领信息？", "取消", "确定")
                    currencyDialog.setDialogClickListener(object :
                        CurrencyDialog.DialogClickListener {
                        override fun onLeftClick() {
                            currencyDialog.dialog.dismiss()
                        }

                        override fun onRightClick() {
                            currencyDialog.dialog.dismiss()
                            deleteConsume(item.id, position)
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
                            approvalConsume(item.id, position, loginUserBean?.nickname)
                        }

                    })

                }
            }
        }
    }

    private fun getQueryDate() {
        val creater = ed_creater.text.toString()
        val materialDate = te_material_date.text.toString()
        getAllPageStorehouse(
            creater, materialDate, selectDeptCode, selectStorehouseCode,
            selectIsApprovalCode, selectIsConsumeCode, currentIndex, pageSize
        )
    }

    private fun getAllPageStorehouse(
        applicant: String,
        consumeDate: String,
        deptCode: String,
        warehouseCode: String,
        isApproval: String,
        isConsume: String,
        pageNum: Int,
        pageSize: Int
    ) {
        allNetViewModel.getAllPageConsume(
            applicant, consumeDate, deptCode, warehouseCode, isApproval, isConsume,
            pageNum, pageSize
        ).observe(this, {
            if (it.isOk()) {
                if (!it.data?.rows.isNullOrEmpty()) {
                    if (currentIndex == 1) {
                        materialRequisitionQueryListAdapter.setNewInstance(it.data?.rows)
                    } else {
                        materialRequisitionQueryListAdapter.addData(it.data?.rows!!)
                    }
                } else {
                    if (currentIndex == 1) {
                        showToast("暂无相关记录")
                        materialRequisitionQueryListAdapter.data.clear()
                        materialRequisitionQueryListAdapter.notifyDataSetChanged()
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

    //删除物资请领信息
    private fun deleteConsume(orderId: String?, position: Int) {
        allNetViewModel.deleteConsume(orderId).observe(this, {
            if (it.isOk()) {
                materialRequisitionQueryListAdapter.removeAt(position)
                showToast("删除成功")
            } else {
                it.msg?.let { msg ->
                    showToast(msg)
                }
            }
        })
    }

    //审核物资请领信息
    private fun approvalConsume(orderId: String?, position: Int, nickname: String?) {
        allNetViewModel.approvalConsume(orderId, nickname).observe(this, {
            if (it.isOk()) {
                materialRequisitionQueryListAdapter.removeAt(position)
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

        fun launchQueryMaterialListActivity(context: Context?) {
            context?.startActivity(Intent(context, QueryMaterialListActivity::class.java).apply {
            })
        }

    }

}
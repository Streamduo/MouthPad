package com.mouth.pad

import android.Manifest
import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.AdapterView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bigkoo.pickerview.builder.TimePickerBuilder
import com.bigkoo.pickerview.listener.OnTimeSelectListener
import com.bigkoo.pickerview.view.TimePickerView
import com.bmncc.pis.ylct.utils.TimeUtils
import com.bmncc.pis.ylct.utils.setOnSingleClickListener
import com.mouth.pad.adapter.ConsumptionQueryListAdapter
import com.mouth.pad.base.BaseActivity
import com.mouth.pad.utils.Const
import com.permissionx.guolindev.PermissionX
import com.xys.libzxing.zxing.activity.CaptureActivity
import kotlinx.android.synthetic.main.activity_consumption_query.*
import kotlinx.android.synthetic.main.layout_title.*

//消耗查询
class ConsumptionQueryActivity : BaseActivity() {

    var currentIndex = 1
    private val pageSize = Const.PAGE_SIZE
    private val allNetViewModel by lazy {
        AllNetViewModel()
    }
    private var selectMaterialCode: String? = ""
    private val consumptionQueryListAdapter by lazy {
        ConsumptionQueryListAdapter(R.layout.item_consumption_list)
    }
    private var pvTime: TimePickerView? = null
    private val onTimeSelectListener = OnTimeSelectListener { date, v ->
        date?.let {
            ed_out_date.text = TimeUtils.getYearDay(it)
        }
        pvTime?.dismiss()
    }
    private var selectDeptCode = ""
    private var selectDeptName = ""
    private var selectStorehouseCode = ""
    private var selectStorehouse = ""


    override fun layoutId(): Int {
        return R.layout.activity_consumption_query
    }

    override fun initData() {
    }

    override fun initView() {
        tv_title.text = "消耗查询"
        title_back.setOnSingleClickListener {
            finish()
        }
        rv_refresh_list.apply {
            layoutManager = LinearLayoutManager(this@ConsumptionQueryActivity)
            adapter = consumptionQueryListAdapter
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
        val type = booleanArrayOf(true, true, true, false, false, false)
        //时间选择器
        pvTime = TimePickerBuilder(this, onTimeSelectListener)
            .setType(type)
            .build()
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
        ed_out_date.setOnSingleClickListener {
            pvTime?.show()
        }
        //扫码
        te_sCode.setOnSingleClickListener {
            getPermissions()
        }
        //重置
        te_reset.setOnSingleClickListener {
            te_search_result.visibility = View.GONE
            consumptionQueryListAdapter.data.clear()
            consumptionQueryListAdapter.notifyDataSetChanged()
            sp_department.setSelection(0)
            sp_storehouse.setSelection(0)
            ed_out_date.text = ""
            te_stuff_name.text = ""
        }
        te_query.setOnSingleClickListener {
            currentIndex = 1
            getQueryDate()
        }
    }

    private fun getQueryDate() {
        val outDate = ed_out_date.text.toString()
        getAllConsumptionQuery(
            selectDeptCode,
            selectMaterialCode,
            selectStorehouseCode,
            currentIndex,
            pageSize
        )
    }

    private fun getAllConsumptionQuery(
        deptCode: String,
        materialCode: String?,
        warehouseCode: String,
        pageNum: Int,
        pageSize: Int
    ) {
        allNetViewModel.getAllConsumptionQuery(
            deptCode, materialCode,
            warehouseCode, pageNum, pageSize
        ).observe(this, Observer {
            if (it.isOk()) {
                if (!it.data?.rows.isNullOrEmpty()) {
                    te_search_result.visibility = View.VISIBLE
                    if (currentIndex == 1) {
                        consumptionQueryListAdapter.setNewInstance(it.data?.rows)
                    } else {
                        consumptionQueryListAdapter.addData(it.data?.rows!!)
                    }
                } else {
                    if (currentIndex == 1) {
                        te_search_result.visibility = View.GONE
                        showToast("暂无相关记录")
                        consumptionQueryListAdapter.data.clear()
                        consumptionQueryListAdapter.notifyDataSetChanged()
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
                selectByMaterialCode(result)
            }
        }
    }

    ////根据材料编号查询-材料基本信息
    private fun selectByMaterialCode(materialCode: String?) {
        allNetViewModel.selectByMaterialCode(materialCode).observe(this, {
            if (it.isOk()) {
                it.data?.apply {
                    te_stuff_name.text = invName
                    selectMaterialCode = materialCode
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

        fun launchConsumptionQueryActivity(context: Context?) {
            context?.startActivity(Intent(context, ConsumptionQueryActivity::class.java).apply {
            })
        }

    }
}
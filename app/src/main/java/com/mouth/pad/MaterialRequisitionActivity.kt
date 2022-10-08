package com.mouth.pad

import android.Manifest
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
import com.google.gson.Gson
import com.mouth.pad.adapter.MaterialRequisitionStuffListAdapter
import com.mouth.pad.base.BaseActivity
import com.mouth.pad.bean.LoginUserBean
import com.mouth.pad.bean.TConsumeDetail
import com.mouth.pad.bean.TMaterialRequisition
import com.mouth.pad.bean.TOrderDetail
import com.mouth.pad.utils.Const
import com.mouth.pad.utils.Logger
import com.mouth.pad.utils.SpUtil
import com.permissionx.guolindev.PermissionX
import com.xys.libzxing.zxing.activity.CaptureActivity
import kotlinx.android.synthetic.main.activity_material_requisition.*
import kotlinx.android.synthetic.main.layout_title_subtitle.*
import java.util.ArrayList

//物资请领
class MaterialRequisitionActivity : BaseActivity() {

    private var pvTime: TimePickerView? = null
    private val onTimeSelectListener = OnTimeSelectListener { date, v ->
        date?.let {
            te_claim_date.text = TimeUtils.getYearDay(it)
        }
        pvTime?.dismiss()
    }

    private val allNetViewModel by lazy {
        AllNetViewModel()
    }
    private var selectDeptCode = ""
    private var selectDeptName = ""
    private var selectStorehouseCode = ""
    private var selectStorehouse = ""
    private val materialRequisitionStuffListAdapter by lazy {
        MaterialRequisitionStuffListAdapter(R.layout.item_material_requisition_stuff_list)
    }
    private var loginUserBean: LoginUserBean? = null

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
            QueryMaterialListActivity.launchQueryMaterialListActivity(this)
        }
        loginUserBean = SpUtil.decodeParcelable(Const.LOGIN_USER_BEAN, LoginUserBean::class.java)
        ed_claimant.setText(loginUserBean?.userName)
        rv_order_list?.apply {
            layoutManager = LinearLayoutManager(this@MaterialRequisitionActivity)
            adapter = materialRequisitionStuffListAdapter
        }
        val storehouseCode = resources.getStringArray(R.array.storehouseCode)
        val storehouse = resources.getStringArray(R.array.storehouse)
        sp_storehouse.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                selectStorehouseCode = storehouseCode[p2]
                selectStorehouse = storehouse[p2]
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

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
        val type = booleanArrayOf(true, true, true, false, false, false)
        //时间选择器
        val pvTime = TimePickerBuilder(this, onTimeSelectListener)
            .setType(type)
            .build()

        te_claim_date.setOnSingleClickListener {
            pvTime.show()
        }
        te_add.setOnSingleClickListener {
            val text = te_add.text
            if (text == "添加") {
                te_add.text = "扫码"
                te_head.visibility = View.VISIBLE
                rl_haed.visibility = View.VISIBLE
                te_search_result.visibility = View.VISIBLE
                te_tips.visibility = View.VISIBLE
                te_send.visibility = View.VISIBLE
            } else {
                getPermissions()
            }
        }
        //发送
        te_send.setOnSingleClickListener {
            val teClaimDate = te_claim_date.text.toString()
            val edClaimant = ed_claimant.text.toString()
            val data = materialRequisitionStuffListAdapter.data
            if (selectStorehouseCode.isEmpty()) {
                showToast("请选择仓库")
                return@setOnSingleClickListener
            }
            if (selectDeptCode.isEmpty()) {
                showToast("请选择科室")
                return@setOnSingleClickListener
            }
            if (teClaimDate.isEmpty()) {
                showToast("请选择申领日期")
                return@setOnSingleClickListener
            }
            if (edClaimant.isEmpty()) {
                showToast("请输入申领人")
                return@setOnSingleClickListener
            }

            if (data.isNullOrEmpty()) {
                showToast("请添加商品")
                return@setOnSingleClickListener
            }
            val orderDetailList: MutableList<TConsumeDetail> = ArrayList()
            for (tMaterial in data) {
                tMaterial.apply {
                    val tConsumeDetail = TConsumeDetail(
                        id,  invCode, invName, invModel,
                        planPrice, unitName, stockNum, "1", noWarehousingNum
                    )
                    orderDetailList.add(tConsumeDetail)
                }
            }
            val tMaterialRequisition = TMaterialRequisition(loginUserBean?.userName,
                teClaimDate, selectDeptCode, selectDeptName, selectStorehouse,
                selectStorehouseCode, edClaimant, orderDetailList
            )
            val gson = Gson()
            val json = gson.toJson(tMaterialRequisition)
            Logger.d(json)
            insertTMaterialRequisition(json)

        }
    }

    //新增-物资请领信息
    private fun insertTMaterialRequisition(json: String) {
        allNetViewModel.insertTMaterialRequisition(json).observe(this, {
            if (it.isOk()) {
                showToast("物资请领成功")
                te_add.text = "添加"
                sp_department.setSelection(0)
                sp_storehouse.setSelection(0)
                ed_claimant.setText("")
                te_claim_date.text = ""
                te_head.visibility = View.GONE
                rl_haed.visibility = View.GONE
                te_send.visibility = View.GONE
                te_search_result.visibility = View.GONE
                rv_order_list.visibility = View.GONE
                materialRequisitionStuffListAdapter.data.clear()
                materialRequisitionStuffListAdapter.notifyDataSetChanged()
            } else {
                it.msg?.let { msg ->
                    showToast(msg)
                }
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
                showLongToast(result)
                selectByMaterialCode(result)
            }
        }
    }

    ////根据材料编号查询-材料基本信息
    private fun selectByMaterialCode(materialCode: String?) {
        allNetViewModel.selectByMaterialCode(materialCode).observe(this, {
            if (it.isOk()) {
                it.data?.apply {
                    te_tips.visibility = View.GONE
                    rv_order_list.visibility = View.VISIBLE
                    materialRequisitionStuffListAdapter.addData(this)
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

        fun launchMaterialRequisitionActivity(context: Context?) {
            context?.startActivity(Intent(context, MaterialRequisitionActivity::class.java).apply {
            })
        }

    }

}
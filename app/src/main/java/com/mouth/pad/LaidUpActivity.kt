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
import com.mouth.pad.adapter.WarehouseStuffListAdapter
import com.mouth.pad.base.BaseActivity
import com.mouth.pad.bean.TStoreHouse
import com.mouth.pad.utils.Logger
import com.permissionx.guolindev.PermissionX
import com.xys.libzxing.zxing.activity.CaptureActivity
import kotlinx.android.synthetic.main.activity_laid_up.*
import kotlinx.android.synthetic.main.activity_laid_up.rl_haed
import kotlinx.android.synthetic.main.activity_laid_up.rv_order_list
import kotlinx.android.synthetic.main.activity_laid_up.sp_department
import kotlinx.android.synthetic.main.activity_laid_up.te_add
import kotlinx.android.synthetic.main.activity_laid_up.te_delete
import kotlinx.android.synthetic.main.activity_laid_up.te_head
import kotlinx.android.synthetic.main.activity_laid_up.te_verify
import kotlinx.android.synthetic.main.layout_title.*

//物资入库
class LaidUpActivity : BaseActivity() {

    private var pvTime: TimePickerView? = null
    private val onTimeSelectListener = OnTimeSelectListener { date, v ->
        date?.let {
            te_invoice_date.text = TimeUtils.getYear(it)
        }
        pvTime?.dismiss()
    }

    private val allNetViewModel by lazy {
        AllNetViewModel()
    }

    private val warehouseStuffListAdapter by lazy {
        WarehouseStuffListAdapter(R.layout.item_warehouse_stuff_list)
    }
    private var selectDeptCode = ""
    private var selectDeptName = ""
    private var selectStorehouseCode = ""
    private var selectStorehouse = ""

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
        rv_order_list?.apply {
            layoutManager = LinearLayoutManager(this@LaidUpActivity)
            adapter = warehouseStuffListAdapter
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

        te_invoice_date.setOnSingleClickListener {
            pvTime.show()
        }

        te_add.setOnSingleClickListener {
            val text = te_add.text
            if (text == "新增") {
                te_add.text = "扫码"
                te_head.visibility = View.VISIBLE
                rl_haed.visibility = View.VISIBLE
                te_save.visibility = View.VISIBLE
            } else {
                getPermissions()
            }
        }
        te_delete.setOnSingleClickListener {
            VerticalListActivity.launchVerticalListActivity(this,3)
        }
        //审核
        te_verify.setOnSingleClickListener {
            VerticalListActivity.launchVerticalListActivity(this,4)
        }
        //保存
        te_save.setOnSingleClickListener {
            val deliveryUnit = te_delivery_unit.text.toString()
            val invoiceDate = te_invoice_date.text.toString()
            val receiptNumber = te_receipt_number.text.toString()
            val data = warehouseStuffListAdapter.data
            if (selectStorehouseCode.isEmpty()) {
                showToast("请选择仓库")
                return@setOnSingleClickListener
            }
            if (selectDeptCode.isEmpty()) {
                showToast("请选择科室")
                return@setOnSingleClickListener
            }
            if (deliveryUnit.isEmpty()) {
                showToast("请输入供货单位")
                return@setOnSingleClickListener
            }
            if (invoiceDate.isEmpty()) {
                showToast("请选择发票日期")
                return@setOnSingleClickListener
            }
            if (receiptNumber.isEmpty()) {
                showToast("请输入发票号")
                return@setOnSingleClickListener
            }

            if (data.isNullOrEmpty()) {
                showToast("请添加商品")
                return@setOnSingleClickListener
            }
            val tConsume = TStoreHouse(
                "1", selectDeptCode, selectDeptName, selectStorehouseCode,
                deliveryUnit, invoiceDate, receiptNumber, data
            )
            val gson = Gson()
            val tConsumeJson = gson.toJson(tConsume)
            Logger.d(tConsumeJson)
            insertStorehouse(tConsumeJson)
        }
    }

    //入库
    private fun insertStorehouse(storehouseInfo:String){
        allNetViewModel.insertStorehouse(storehouseInfo).observe(this,{
            if (it.isOk()){
                showToast("入库成功")
                sp_department.setSelection(0)
                sp_storehouse.setSelection(0)
                te_delivery_unit.setText("")
                te_invoice_date.text = ""
                te_receipt_number.setText("")
                te_head.visibility = View.GONE
                rl_haed.visibility = View.GONE
                te_save.visibility = View.GONE
                te_order_detail.visibility = View.GONE
                rv_order_list.visibility = View.GONE
                warehouseStuffListAdapter.data.clear()
                warehouseStuffListAdapter.notifyDataSetChanged()
            }else{
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
                    te_order_detail.visibility = View.VISIBLE
                    rv_order_list.visibility = View.VISIBLE
                    warehouseStuffListAdapter.addData(this)
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

        fun launchLaidUpActivity(context: Context?) {
            context?.startActivity(Intent(context, LaidUpActivity::class.java).apply {
            })
        }

    }
}
package com.mouth.pad

import android.content.Context
import android.content.Intent
import androidx.recyclerview.widget.LinearLayoutManager
import com.bmncc.pis.ylct.utils.setOnSingleClickListener
import com.mouth.pad.adapter.MaterialRequisitionQueryListAdapter
import com.mouth.pad.adapter.OrderQueryListAdapter
import com.mouth.pad.adapter.WarehouseQueryListAdapter
import com.mouth.pad.base.BaseActivity
import com.mouth.pad.bean.LoginUserBean
import com.mouth.pad.utils.Const
import com.mouth.pad.utils.SpUtil
import com.mouth.pad.view.CurrencyDialog
import kotlinx.android.synthetic.main.activity_vertical_list.*

class VerticalListActivity : BaseActivity() {

    private var listType = 0
    private val allNetViewModel by lazy {
        AllNetViewModel()
    }

    private val orderQueryListAdapter by lazy {
        OrderQueryListAdapter(R.layout.item_query_order_list)
    }

    private val warehouseQueryListAdapter by lazy {
        WarehouseQueryListAdapter(R.layout.item_query_warehouse_list)
    }

    private val materialRequisitionQueryListAdapter by lazy {
        MaterialRequisitionQueryListAdapter(R.layout.item_query_material_requisition_list)
    }

    override fun layoutId(): Int {
        return R.layout.activity_vertical_list
    }

    override fun initData() {
    }

    override fun initView() {
        listType = intent.getIntExtra(LIST_TYPE, 0)
        iv_back.setOnSingleClickListener {
            finish()
        }
        val loginUserBean =
            SpUtil.decodeParcelable(Const.LOGIN_USER_BEAN, LoginUserBean::class.java)
        rv_vertical.layoutManager = LinearLayoutManager(this)
        when (listType) {
            0 -> {//订单查询
                te_title.text = "订单查询"
                rv_vertical.adapter = orderQueryListAdapter
                getAllOrder()
            }
            1 -> {//订单查询&删除
                te_title.text = "订单删除"
                rv_vertical.adapter = orderQueryListAdapter
                orderQueryListAdapter.isDelete = true
                orderQueryListAdapter.addChildClickViewIds(R.id.te_delete)
                getAllOrder()
            }
            2 -> {//订单查询&审核
                te_title.text = "订单审核"
                rv_vertical.adapter = orderQueryListAdapter
                orderQueryListAdapter.isCheck = true
                orderQueryListAdapter.addChildClickViewIds(R.id.te_check)
                getAllOrder()
            }
            3 -> {//入库信息查询&删除
                te_title.text = "入库删除"
                rv_vertical.adapter = warehouseQueryListAdapter
                warehouseQueryListAdapter.isDelete = true
                warehouseQueryListAdapter.addChildClickViewIds(R.id.te_delete)
                getAllStorehouse()
            }
            4 -> {//入库信息查询&审核
                te_title.text = "入库审核"
                rv_vertical.adapter = warehouseQueryListAdapter
                warehouseQueryListAdapter.isCheck = true
                warehouseQueryListAdapter.addChildClickViewIds(R.id.te_check)
                getAllStorehouse()
            }
            5 -> {//物资请领信息查询&删除
                te_title.text = "物资请领删除"
                rv_vertical.adapter = materialRequisitionQueryListAdapter
                materialRequisitionQueryListAdapter.isDelete = true
                materialRequisitionQueryListAdapter.addChildClickViewIds(R.id.te_delete)
                getAllConsume()
            }
            6 -> {//物资请领查询&审核
                te_title.text = "物资请领审核"
                rv_vertical.adapter = materialRequisitionQueryListAdapter
                materialRequisitionQueryListAdapter.isCheck = true
                materialRequisitionQueryListAdapter.addChildClickViewIds(R.id.te_check)
                getAllConsume()
            }

        }
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
                            approvalOrder(item.id, position, loginUserBean?.nickname)
                        }

                    })

                }
            }
        }

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

    //查询全部订单
    private fun getAllOrder() {
        allNetViewModel.getAllOrder().observe(this, {
            if (it.isOk()) {
                if (!it.data.isNullOrEmpty()) {
                    orderQueryListAdapter.setNewInstance(it.data)
                }
            } else {
                it.msg?.let { msg ->
                    showToast(msg)
                }
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

    //查询全部入库信息
    private fun getAllStorehouse() {
        allNetViewModel.getAllStorehouse().observe(this, {
            if (it.isOk()) {
                if (!it.data.isNullOrEmpty()) {
                    warehouseQueryListAdapter.setNewInstance(it.data)
                }
            } else {
                it.msg?.let { msg ->
                    showToast(msg)
                }
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

    //查询全部物资请领信息
    private fun getAllConsume() {
        allNetViewModel.getAllConsume().observe(this, {
            if (it.isOk()) {
                if (!it.data.isNullOrEmpty()) {
                    materialRequisitionQueryListAdapter.setNewInstance(it.data)
                }
            } else {
                it.msg?.let { msg ->
                    showToast(msg)
                }
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

        const val LIST_TYPE = "listType"

        fun launchVerticalListActivity(context: Context?, listType: Int) {
            context?.startActivity(Intent(context, VerticalListActivity::class.java).apply {
                putExtra(LIST_TYPE, listType)
            })
        }

    }

}
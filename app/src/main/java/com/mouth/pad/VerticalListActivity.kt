package com.mouth.pad

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.bmncc.pis.ylct.utils.setOnSingleClickListener
import com.mouth.pad.adapter.OrderQueryListAdapter
import com.mouth.pad.base.BaseActivity
import kotlinx.android.synthetic.main.activity_vertical_list.*

class VerticalListActivity : BaseActivity() {

    private var listType = 0
    private val allNetViewModel by lazy {
        AllNetViewModel()
    }

    private val orderQueryListAdapter by lazy {
        OrderQueryListAdapter(R.layout.item_query_order_list)
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
        }
        orderQueryListAdapter.setOnItemChildClickListener { _, view, position ->
            val item = orderQueryListAdapter.getItem(position)
            if (view.id == R.id.te_delete) {
                deleteOrder(item.orderNo, position)
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
package com.mouth.pad.adapter


import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.mouth.pad.R
import com.mouth.pad.bean.TOrderQueryListBean

class OrderQueryListAdapter(layoutResId: Int) :
    BaseQuickAdapter<TOrderQueryListBean, BaseViewHolder>(layoutResId) {

    private val stuffListAdapter = StuffListAdapter(R.layout.item_stuff_list_small)
    var isDelete = false

    override fun convert(holder: BaseViewHolder, item: TOrderQueryListBean) {
        val rvStuffList = holder.getView<RecyclerView>(R.id.rv_stuff_list)
        rvStuffList.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        );
        rvStuffList.layoutManager = LinearLayoutManager(context)
        rvStuffList.adapter = stuffListAdapter
        holder.setVisible(R.id.te_delete, isDelete)
        item.apply {
            holder.setText(R.id.te_order_number, orderNo)
            holder.setText(R.id.te_order_date, createTime)
            holder.setText(R.id.te_total_order, orderAmount)
            holder.setText(R.id.te_order_status, orderState)
            holder.setText(R.id.te_department, deptName)
            holder.setText(R.id.te_delivery_unit, supplier)
            holder.setText(R.id.te_buyer, buyer)
            stuffListAdapter.setNewInstance(orderDetailList)
        }
    }
}
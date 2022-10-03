package com.mouth.pad.adapter


import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.mouth.pad.R
import com.mouth.pad.bean.TMaterialRequisitionQueryListBean

class MaterialRequisitionQueryListAdapter(layoutResId: Int) :
    BaseQuickAdapter<TMaterialRequisitionQueryListBean, BaseViewHolder>(layoutResId) {

    var isDelete = false
    var isCheck = false

    override fun convert(holder: BaseViewHolder, item: TMaterialRequisitionQueryListBean) {
        val rvStuffList = holder.getView<RecyclerView>(R.id.rv_stuff_list)
        rvStuffList.layoutManager = LinearLayoutManager(context)
        val stuffListAdapter = MaterialRequisitionStuffSmallListAdapter(R.layout.item_material_requisition_stuff_list_small)
        rvStuffList.adapter = stuffListAdapter
        if (item.isApproval == "0") {
            holder.setGone(R.id.te_delete, false)
            holder.setGone(R.id.te_check, false)
        } else {
            holder.setGone(R.id.te_delete, false)
            holder.setGone(R.id.te_check, false)
        }
        item.apply {
            holder.setText(R.id.te_create_date, consumeDate)
            holder.setText(R.id.te_storehouse, warehouseName)
            holder.setText(R.id.te_department, deptName)
            holder.setText(R.id.te_claimant, applicant)
            stuffListAdapter.setNewInstance(consumeDetailList)
        }
    }
}
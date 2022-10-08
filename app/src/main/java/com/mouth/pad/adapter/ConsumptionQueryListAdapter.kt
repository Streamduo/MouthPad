package com.mouth.pad.adapter


import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.mouth.pad.R
import com.mouth.pad.bean.TConsumptionQueryDetail
import com.mouth.pad.bean.TMaterial

class ConsumptionQueryListAdapter(layoutResId: Int) : BaseQuickAdapter<TConsumptionQueryDetail, BaseViewHolder>(layoutResId) {

    override fun convert(holder: BaseViewHolder, item: TConsumptionQueryDetail) {
        item.apply {
            holder.setText(R.id.te_material_num, materiaCode)
            holder.setText(R.id.te_material, materialName)
            holder.setText(R.id.te_specification, model)
            holder.setText(R.id.te_picking_department, deptName)
            holder.setText(R.id.te_total_size, consumeNum)
            holder.setText(R.id.te_amount, unitPrice)
        }
    }
}
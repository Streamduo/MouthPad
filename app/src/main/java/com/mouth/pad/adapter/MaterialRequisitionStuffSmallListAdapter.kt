package com.mouth.pad.adapter


import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.mouth.pad.R
import com.mouth.pad.bean.MaterialListBean
import com.mouth.pad.bean.TConsumeDetail
import com.mouth.pad.bean.TMaterial

class MaterialRequisitionStuffSmallListAdapter(layoutResId: Int) :
    BaseQuickAdapter<TConsumeDetail, BaseViewHolder>(layoutResId) {

    override fun convert(holder: BaseViewHolder, item: TConsumeDetail) {
        item.apply {
            holder.setText(R.id.te_material, materialName)
            holder.setText(R.id.te_specification, model)
            holder.setText(R.id.te_physical_inventory, realStockNum)
            holder.setText(R.id.te_quantity, "1")
            holder.setText(R.id.te_available_stock, realStockNum)
            holder.setText(R.id.te_unit, unit)
        }
        holder.setVisible(R.id.view_gray, holder.bindingAdapterPosition != data.size - 1)
    }
}
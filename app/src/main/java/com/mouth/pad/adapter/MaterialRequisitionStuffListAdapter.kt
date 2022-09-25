package com.mouth.pad.adapter


import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.mouth.pad.R
import com.mouth.pad.bean.TMaterial

class MaterialRequisitionStuffListAdapter(layoutResId: Int) : BaseQuickAdapter<TMaterial, BaseViewHolder>(layoutResId) {

    override fun convert(holder: BaseViewHolder, item: TMaterial) {
        item.apply {
            holder.setText(R.id.te_material, invName)
            holder.setText(R.id.te_specification, invModel)
            holder.setText(R.id.te_physical_inventory, stockNum)
            //TODO 可领用库存 未知
            holder.setText(R.id.te_available_stock, noWarehousingNum)
            holder.setText(R.id.te_manufacturer, unitName)
            holder.setText(R.id.te_unit, unitName)
            holder.setText(R.id.te_quantity, "1")
        }
    }
}
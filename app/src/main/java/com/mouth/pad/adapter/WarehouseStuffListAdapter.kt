package com.mouth.pad.adapter


import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.mouth.pad.R
import com.mouth.pad.bean.TMaterial

class WarehouseStuffListAdapter(layoutResId: Int) :
    BaseQuickAdapter<TMaterial, BaseViewHolder>(layoutResId) {

    override fun convert(holder: BaseViewHolder, item: TMaterial) {
        item.apply {
            holder.setText(R.id.te_material, invName)
            holder.setText(R.id.te_specification, invModel)
            holder.setText(R.id.te_amount, planPrice + "元")
            holder.setText(R.id.te_num, "1")
            holder.setText(R.id.te_price, planPrice + "元")
        }
    }
}
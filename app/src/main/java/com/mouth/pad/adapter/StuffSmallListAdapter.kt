package com.mouth.pad.adapter


import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.mouth.pad.R
import com.mouth.pad.bean.MaterialListBean
import com.mouth.pad.bean.TMaterial

class StuffSmallListAdapter(layoutResId: Int) :
    BaseQuickAdapter<MaterialListBean, BaseViewHolder>(layoutResId) {

    override fun convert(holder: BaseViewHolder, item: MaterialListBean) {
        item.apply {
            holder.setText(R.id.te_material, materialName)
            holder.setText(R.id.te_specification, model)
            holder.setText(R.id.te_unit, unit)
            holder.setText(R.id.te_num, "1")
            holder.setText(R.id.te_price, unitPrice)
        }
        holder.setVisible(R.id.view_gray, holder.bindingAdapterPosition != data.size - 1)
    }
}
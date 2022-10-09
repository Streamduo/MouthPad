package com.mouth.pad.adapter


import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.mouth.pad.R
import com.mouth.pad.bean.MaterialListBean
import com.mouth.pad.bean.TMaterial
import com.mouth.pad.bean.TReceiptDetail

class WarehouseSmallListAdapter(layoutResId: Int) :
    BaseQuickAdapter<TReceiptDetail, BaseViewHolder>(layoutResId) {

    override fun convert(holder: BaseViewHolder, item: TReceiptDetail) {
        item.apply {
            holder.setText(R.id.te_material, materialName)
            holder.setText(R.id.te_specification, model)
            holder.setText(R.id.te_amount, balanceAmount + "元")
            holder.setText(R.id.te_num, stockNum)
            holder.setText(R.id.te_price, unitPrice + "元")
        }
        holder.setVisible(R.id.view_gray, holder.bindingAdapterPosition != data.size - 1)
    }
}
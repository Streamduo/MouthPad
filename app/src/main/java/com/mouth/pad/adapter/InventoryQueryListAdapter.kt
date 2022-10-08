package com.mouth.pad.adapter


import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.mouth.pad.R
import com.mouth.pad.bean.TConsumptionQueryDetail
import com.mouth.pad.bean.TInventoryQueryDetail
import com.mouth.pad.bean.TMaterial

class InventoryQueryListAdapter(layoutResId: Int) : BaseQuickAdapter<TInventoryQueryDetail, BaseViewHolder>(layoutResId) {

    override fun convert(holder: BaseViewHolder, item: TInventoryQueryDetail) {
        item.apply {
            holder.setText(R.id.te_select_storehouse, warehouseName)
            holder.setText(R.id.te_material_code, materialCode)
            holder.setText(R.id.te_specifications, model)
            holder.setText(R.id.te_price, unitPrice)
            holder.setText(R.id.te_balance_quantity, stockNum)
            holder.setText(R.id.te_balance_amount, unitPrice)
            holder.setText(R.id.te_measurement, unit)
            holder.setText(R.id.te_manufacturer, factoryName)
        }
    }
}
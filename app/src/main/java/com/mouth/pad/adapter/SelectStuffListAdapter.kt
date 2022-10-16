package com.mouth.pad.adapter


import android.widget.TextView
import android.widget.Toast
import com.bmncc.pis.ylct.utils.setOnSingleClickListener
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.mouth.pad.R
import com.mouth.pad.bean.TMaterial
import java.math.BigDecimal

class SelectStuffListAdapter(layoutResId: Int) :
    BaseQuickAdapter<TMaterial, BaseViewHolder>(layoutResId) {

    override fun convert(holder: BaseViewHolder, item: TMaterial) {
        item.apply {
            holder.setText(R.id.te_material_num, invCode)
            holder.setText(R.id.te_material, invName)
            holder.setText(R.id.te_specification, invModel)
            holder.setText(R.id.te_unit, unitName)
            holder.setText(R.id.te_price, planPrice + "元")
            holder.setText(R.id.te_amount, planPrice + "元")
        }
    }
}
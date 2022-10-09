package com.mouth.pad.adapter


import android.widget.TextView
import android.widget.Toast
import com.bmncc.pis.ylct.utils.setOnSingleClickListener
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.mouth.pad.R
import com.mouth.pad.bean.TMaterial
import java.math.BigDecimal

class OrderStuffListAdapter(layoutResId: Int) :
    BaseQuickAdapter<TMaterial, BaseViewHolder>(layoutResId) {

    override fun convert(holder: BaseViewHolder, item: TMaterial) {
        item.apply {
            holder.setText(R.id.te_material_num, invCode)
            holder.setText(R.id.te_material, invName)
            holder.setText(R.id.te_specification, invModel)
            holder.setText(R.id.te_unit, unitName)
            holder.setText(R.id.te_price, planPrice + "元")
            holder.setText(R.id.te_amount, planPrice + "元")
            stuffNum = 1
            holder.getView<TextView>(R.id.iv_reduce).setOnSingleClickListener {
                stuffNum--
                if (stuffNum <= 0) {
                    Toast.makeText(context, "最小数量为1", Toast.LENGTH_SHORT).show()
                    stuffNum++
                }
                holder.setText(R.id.te_num, stuffNum.toString())
                val bigDecimal = planPrice?.toBigDecimal()
                val amount = bigDecimal?.multiply(BigDecimal(stuffNum))
                holder.setText(R.id.te_amount, amount.toString() + "元")
            }
            holder.getView<TextView>(R.id.iv_add).setOnSingleClickListener {
                stuffNum++
                holder.setText(R.id.te_num, stuffNum.toString())
                val bigDecimal = planPrice?.toBigDecimal()
                val amount = bigDecimal?.multiply(BigDecimal(stuffNum))
                holder.setText(R.id.te_amount, amount.toString() + "元")
            }
            holder.setText(R.id.te_num, stuffNum.toString())
        }
    }
}
package com.mouth.pad.adapter


import android.widget.TextView
import android.widget.Toast
import com.bmncc.pis.ylct.utils.setOnSingleClickListener
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.mouth.pad.R
import com.mouth.pad.bean.TMaterial
import com.mouth.pad.bean.TMaterialStorehouseBean

class MaterialRequisitionStuffListAdapter(layoutResId: Int) : BaseQuickAdapter<TMaterialStorehouseBean, BaseViewHolder>(layoutResId) {

    override fun convert(holder: BaseViewHolder, item: TMaterialStorehouseBean) {
        item.apply {
            holder.setText(R.id.te_material_num, materialCode)
            holder.setText(R.id.te_material, materialName)
            holder.setText(R.id.te_specification, model)
            holder.setText(R.id.te_available_stock, stockNum.toString())
            holder.setText(R.id.te_manufacturer, factoryName)
            holder.setText(R.id.te_unit, unit)
            stuffNum = 1
            holder.getView<TextView>(R.id.iv_reduce).setOnSingleClickListener {
                stuffNum--
                if (stuffNum <= 0) {
                    Toast.makeText(context, "最小数量为1", Toast.LENGTH_SHORT).show()
                    stuffNum++
                }
                holder.setText(R.id.te_num, stuffNum.toString())
            }
            holder.getView<TextView>(R.id.iv_add).setOnSingleClickListener {
                stuffNum++
                if (stuffNum > stockNum) {
                    Toast.makeText(context, "超过最大添加数量", Toast.LENGTH_SHORT).show()
                    stuffNum--
                }
                holder.setText(R.id.te_num, stuffNum.toString())
            }
            holder.setText(R.id.te_num, stuffNum.toString())
        }
    }
}
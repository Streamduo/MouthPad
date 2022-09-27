package com.mouth.pad.bean

import android.os.Parcelable
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize

/**
 * @Author wuxiao
 * @Date 2022-09-18 21:15:19
 * @Description: 订单信息表
 */
class TOrderQueryListBean(
    var id: String? = null,
    /**
     * 订单编号
     */
    //("订单编号")
    var orderNo: String? = null,
    /**
     * 科室编号
     */
    //("科室编号")
    var deptCode: String? = null,

    /**
     * 科室名称
     */
    //("科室名称")
    var deptName: String? = null,

    /**
     * 采购员
     */
    //("采购员")
    var buyer: String? = null,

    /**
     * 供应商
     */
    //("供应商")
    var supplier: String? = null,
    var orderDetailList: MutableList<TOrderDetail>? = ArrayList(),
    var createTime: String? = null,
    var orderAmount: String? = null,
    var orderState: String? = null,
)
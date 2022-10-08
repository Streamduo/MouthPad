package com.mouth.pad.bean

import java.util.*

/**
 * @Author wuxiao
 * @Date 2022-09-18 21:15:19
 * @Description: 订单信息表
 */
class TOrder(
    //创建人
    var createBy: String? = null,
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
    var orderDetailList: MutableList<TOrderDetail>? = null
) {

}
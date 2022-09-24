package com.mouth.pad.bean

/**
 * @Author wuxiao
 * @Date 2022-09-18 21:15:19
 * @Description: 订单信息表
 */
class MaterialListBean(

    /**
     * 材料编码
     */
    var materiaCode: String? = null,

    /**
     * 材料名称
     */
    var materialName: String? = null,

    /**
     * 规格型号
     */
    var model: String? = null,


    /**
     * 单价
     */
    var unitPrice: String? = null,

    /**
     * 单位
     */
    var unit: String? = null,

    /**
     * 库存
     */
    //("供应商")
    var stockNum: String? = null,

    /**
     * 结存金额
     */
    var balanceAmount: String? = null,

    /**
     * 待入库数量
     */
    var noWarehousingNum: String? = null


)
package com.mouth.pad.bean

/**
 * @Author wuxiao
 * @Date 2022-09-18 21:15:19
 * @Description: 物资请领明细表
 */
class TConsumeDetail (
    var id: String? = null,
    /**
     * 创建时间
     */
    var createTime: String? = null,
    /**
     * 材料编码
     */
    //("材料编码")
    var materiaCode: String? = null,

    /**
     * 材料名称
     */
    //("材料名称")
    var materialName: String? = null,

    /**
     * 规格型号
     */
    //("规格型号")
    var model: String? = null,

    /**
     * 单价
     */
    //("单价")
    var unitPrice: String? = null,

    /**
     * 单位
     */
    //("单位")
    var unit: String? = null,

    /**
     * 实际库存数量
     */
    //("实际库存数量")
    var realStockNum: String? = null,

    /**
     * 请领数量
     */
    //("请领数量")
    var consumeNum: String? = null,

    /**
     * 待入库数量
     */
    //("待入库数量")
    var noWarehousingNum: String? = null,

)
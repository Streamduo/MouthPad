package com.mouth.pad.bean

/**
 * @Author wuxiao
 * @Date 2022-09-20 14:08:57
 * @Description:
 */
class TMaterial (
    var id: String? = null,
    var createTime: String? = null,

    /**
     *
     */
    //("材料条码编号")
    var invCode: String? = null,

    /**
     *
     */
    //("")
    var invId: String? = null,

    /**
     *
     */
    //("")
    var compCode: String? = null,

    /**
     *
     */
    //("")
    var copyCode: String? = null,

    /**
     *
     */
    //("材料名称")
    var invName: String? = null,

    /**
     *
     */
    //("")
    var spell: String? = null,

    /**
     *
     */
    //("规格型号")
    var invModel: String? = null,

    /**
     *
     */
    //("单位编码")
    var unitCode: String? = null,

    /**
     *
     */
    //("单位名称")
    var unitName: String? = null,

    /**
     *
     */
    //("")
    var unitCodeF: String? = null,

    /**
     *
     */
    //("")
    var mateTypeCode: String? = null,

    /**
     *
     */
    //("")
    var factoryCode: String? = null,

    /**
     *
     */
    //("")
    var invAttrCode: String? = null,

    /**
     *
     */
    //("")
    var batchNo: String? = null,

    /**
     *
     */
    //("")
    var curStock: String? = null,

    /**
     *
     */
    //("")
    var price: String? = null,

    /**
     *
     */
    //("")
    var pricRate: String? = null,

    /**
     *
     */
    //("单价")
    var planPrice: String? = null,

    /**
     *
     */
    //("")
    var refeCost: String? = null,

    /**
     *
     */
    //("")
    var cusCode: String? = null,

    /**
     *
     */
    //("")
    var buyAhead: String? = null,

    /**
     *
     */
    //("")
    var ecoBat: String? = null,

    /**
     *
     */
    //("")
    var abc: String? = null,

    /**
     *
     */
    //("")
    var stockSecu: String? = null,

    /**
     *
     */
    //("")
    var lowLimit: String? = null,

    /**
     *
     */
    //("")
    var highLimit: String? = null,

    /**
     *
     */
    //("")
    var stayTime: String? = null,

    /**
     *
     */
    //("")
    var perWeight: String? = null,

    /**
     *
     */
    //("")
    var perVolum: String? = null,

    /**
     *
     */
    //("")
    var sdate: String? = null,

    /**
     *
     */
    //("")
    var edate: String? = null,

    /**
     *
     */
    //("")
    var isCert: String? = null,

    /**
     *
     */
    //("")
    var brandName: String? = null,

    /**
     *
     */
    //("")
    var agentName: String? = null,

    /**
     *
     */
    //("")
    var alias: String? = null,

    /**
     *
     */
    //("")
    var refPrice: String? = null,

    /**
     *
     */
    //("")
    var isBar: String? = null,

    /**
     *
     */
    //("")
    var barCodeNew: String? = null,

    /**
     *
     */
    //("")
    var cusName: String? = null,

    /**
     * 库存数量
     */
    //("库存数量")
    var stockNum: String? = null,

    /**
     * 结存金额
     */
    //("结存金额")
    var balanceAmount: String? = null,

    /**
     * 待入库数量
     */
    //("待入库数量")
    var noWarehousingNum: Int = 0,
    /**
     * 材料数量
     */
    //("库存数量")
    var stuffNum: Int = 0,
)
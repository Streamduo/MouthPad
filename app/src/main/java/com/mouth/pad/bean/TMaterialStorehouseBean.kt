package com.mouth.pad.bean

/**
 *

 * @ClassName:      TMaterialStorehouseBean
 * @Description:     java类作用描述
 * @Author:         Fuduo
 * @CreateDate:     2022/10/9 14:17
 * @UpdateUser:     更新者
 * @UpdateDate:     2022/10/9 14:17
 * @UpdateRemark:   更新说明
 * @Version:        1.0
 */
class TMaterialStorehouseBean(
    var id: String? = null,

    //创建人
    var createBy: String? = null,
    /**
     * 创建时间
     */
    var createTime: String? = null,
    /**
     * 材料编码
     */
    //("材料编码")
    var materialCode: String? = null,

    /**
     * 材料名称
     */
    //("材料名称")
    var materialName: String? = null,

    /**
     * 仓库编码
     */
    //("仓库编码")
    var warehouseCode: String? = null,

    /**
     * 仓库名称
     */
    var warehouseName: String? = null,

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
     * 计量单位
     */
    var unit: String? = null,

    /**
     * 结存数量
     */
    var stockNum: Int = 0,
    var factoryCode: String? = null,
    //生产厂商
    var factoryName: String? = null,
    //结存金额
    var balanceAmount: String? = null,
    /**
     * 材料数量
     */
    var stuffNum: Int = 0,
)
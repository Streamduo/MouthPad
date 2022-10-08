package com.mouth.pad.bean

/**
 *

 * @ClassName:      TorderPageListBean
 * @Description:     java类作用描述
 * @Author:         Fuduo
 * @CreateDate:     2022/10/3 10:58
 * @UpdateUser:     更新者
 * @UpdateDate:     2022/10/3 10:58
 * @UpdateRemark:   更新说明
 * @Version:        1.0
 */
data class InventoryQueryPageListBean(
    var rows: MutableList<TInventoryQueryDetail> = ArrayList()
)

class TInventoryQueryDetail(

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
    var stockNum: String? = null,
    var factoryCode: String? = null,
    //生产厂商
    var factoryName: String? = null,
)
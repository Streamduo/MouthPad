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
data class ConsumptionQueryPageListBean(
    var rows: MutableList<TConsumptionQueryDetail> = ArrayList()
)

class TConsumptionQueryDetail(

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
    var materiaCode: String? = null,

    /**
     * 材料名称
     */
    //("材料名称")
    var materialName: String? = null,

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
     * 单位
     */
    //("单位")
    var unit: String? = null,

    /**
     * 请领数量
     */
    //("请领数量")
    var consumeNum: String? = null
)
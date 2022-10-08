package com.mouth.pad.bean

/**
 * @Author wuxiao
 * @Date 2022-09-18 21:15:19
 * @Description: 物资请领信息表
 */
class TMaterialRequisition(

    //创建人
    var createBy: String? = null,

    /**
     * 请领日期
     */
    var consumeDate: String? = null,

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

    //仓库名称
    var warehouseName: String? = null,

    /**
     * 请领人
     */
    var applicant: String? = null,

    var consumeDetailList: MutableList<TConsumeDetail>? = null,
)
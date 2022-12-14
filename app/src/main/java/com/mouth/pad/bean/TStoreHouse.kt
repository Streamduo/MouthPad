package com.mouth.pad.bean

/**
 * @Author wuxiao
 * @Date 2022-09-18 21:15:19
 * @Description:
 */
class TStoreHouse (
    //创建人
    var createBy: String? = null,
    /**
     * 业务类型
     */
    var businessType: String? = null,

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
     * 供应商
     */
    //("供应商")
    var supplier: String? = null,
    /**
     * 发票日期
     */
    var invoiceDate: String? = null,

    /**
     * 发票号
     */
    var invoiceNo: String? = null,
    /**
     * 制单人
     */
    var applicant: String? = null,

    var receiptDetailList: MutableList<TReceiptDetail>? = null,
)
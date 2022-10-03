package com.mouth.pad.bean

import android.os.Parcelable
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize

/**
 * @Author wuxiao
 * @Date 2022-09-18 21:15:19
 * @Description: 订单信息表
 */
class TStoreHouseQueryListBean(
    var id: String? = null,
    var createTime: String? = null,
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

    /**
     * 仓库名称
     */
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
     * 审核人
     */
    var reviewer: String? = null,

    /**
     * 审核状态 0-未审核 1-已审核
     */
    //("审核状态 0-未审核 1-已审核")
    var isApproval: String? = null,

    var receiptDetailList: MutableList<TReceiptDetail>? = null,

    )
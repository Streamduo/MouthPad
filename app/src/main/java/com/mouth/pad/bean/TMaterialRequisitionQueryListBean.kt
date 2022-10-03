package com.mouth.pad.bean

import android.os.Parcelable
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize

/**
 * @Author wuxiao
 * @Date 2022-09-18 21:15:19
 * @Description: 订单信息表
 */
class TMaterialRequisitionQueryListBean(
    var id: String? = null,
    //申领日期
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

    /**
     * 仓库名称
     */
    var warehouseName: String? = null,

    /**
     * 申领人
     */
    var applicant: String? = null,

    /**
     * 审核人
     */
    var reviewer: String? = null,

    var consumeDetailList: MutableList<TConsumeDetail>? = null,

    /**
     * 审核状态 0-未审核 1-已审核
     */
    //("审核状态 0-未审核 1-已审核")
    var isApproval: String? = null

    )
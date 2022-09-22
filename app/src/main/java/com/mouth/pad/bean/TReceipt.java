package com.mouth.pad.bean;


import java.util.ArrayList;
import java.util.Date;
import java.math.BigDecimal;
import java.util.List;

/**
 * @Author wuxiao
 * @Date 2022-09-18 21:15:19
 * @Description: 入库信息表
 */
public class TReceipt {

    /**
     * 入库单号
     */
    //("入库单号")
    private String receiptNo;
    /**
     * 业务类型
     */
    //("业务类型")
    private String businessType;
    /**
     * 仓库编码
     */
    //("仓库编码")
    private String warehouseCode;
    /**
     * 科室编码
     */
    //("科室编码")
    private String deptCode;
    /**
     * 供货单位
     */
    //("供货单位")
    private String supplier;
    /**
     * 订单编号
     */
    //("订单编号")
    private String orderNo;
    /**
     * 发票日期
     */
    //("发票日期")
    private Date invoiceDate;
    /**
     * 发票号
     */
    //("发票号")
    private String invoiceNo;
    /**
     * 审核状态 0-未审核 1-已审核
     */
    //("审核状态 0-未审核 1-已审核")
    private String isApproval;
    /**
     * 制单人
     */
    //("制单人")
    private String applicant;
    /**
     * 审核人
     */
    //("审核人")
    private String reviewer;

}
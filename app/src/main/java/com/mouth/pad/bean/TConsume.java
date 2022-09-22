package com.mouth.pad.bean;



import java.util.ArrayList;
import java.util.Date;
import java.math.BigDecimal;
import java.util.List;

/**
 * @Author wuxiao
 * @Date 2022-09-18 21:15:19
 * @Description: 物资请领信息表
 */
public class TConsume{

    /**
     * 请领编号
     */
    //("请领编号")
    private String consumeNo;
    /**
     * 申请日期
     */
    //("申请日期")
    private Date consumeDate;
    /**
     * 科室编号
     */
    //("科室编号")
    private String deptCode;
    /**
     * 科室名称
     */
    //("科室名称")
    private String deptName;
    /**
     * 申请人
     */
    //("申请人")
    private String applicant;
    /**
     * 仓库编码
     */
    //("仓库编码")
    private String warehouseCode;
    /**
     * 订单状态 0-未入库 1-入库中 2-已入库
     */
    //("订单状态 0-未入库 1-入库中 2-已入库")
    private String orderState;
    /**
     * 是否审核
     */
    //("是否审核")
    private String isApproval;
    /**
     * 审核人
     */
    //("审核人")
    private String reviewer;
    /**
     * 是否出库
     */
    //("是否出库")
    private String isConsume;

}
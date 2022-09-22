package com.mouth.pad.bean;


/**
 * @Author wuxiao
 * @Date 2022-09-18 21:15:19
 * @Description: 入库材料明细信息表
 */
public class TReceiptDetail{

    /**
     * 材料编码
     */
    //("材料编码")
    private String materialCode;
    /**
     * 材料名称
     */
    //("材料名称")
    private String materialName;
    /**
     * 规格型号
     */
    //("规格型号")
    private String model;
    /**
     * 单价
     */
    //("单价")
    private Double unitPrice;
    /**
     * 单位
     */
    //("单位")
    private String unit;
    /**
     * 库存数量
     */
    //("库存数量")
    private Integer stockNum;
    /**
     * 结存金额
     */
    //("结存金额")
    private Double balanceAmount;
    /**
     * 待入库数量
     */
    //("待入库数量")
    private String noWarehousingNum;
    /**
     * 入库单号
     */
    //("入库单号")
    private String receiptNo;
}
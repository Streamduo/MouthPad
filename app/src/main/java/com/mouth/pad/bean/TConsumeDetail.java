package com.mouth.pad.bean;

/**
 * @Author wuxiao
 * @Date 2022-09-18 21:15:19
 * @Description: 物资请领明细表
 */
public class TConsumeDetail {

    /**
     * 材料编码
     */
    //("材料编码")
    private String materiaCode;
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
     * 实际库存数量
     */
    //("实际库存数量")
    private Integer realStockNum;
    /**
     * 请领数量
     */
    //("请领数量")
    private Integer consumeNum;
    /**
     * 待入库数量
     */
    //("待入库数量")
    private String noWarehousingNum;
    /**
     * 订单编号
     */
    //("订单编号")
    private String orderNo;
    /**
     * 入库单号
     */
    //("入库单号")
    private String receiptNo;
    /**
     * 0-未入库 1-已入库
     */
    //("0-未入库 1-已入库")
    private String receiptState;
}
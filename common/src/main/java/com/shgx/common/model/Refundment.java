package com.shgx.common.model;

import lombok.Data;

import java.util.Date;

/**
 * @author: guangxush
 * @create: 2019/10/15
 */
@Data
public class Refundment {
    /**
     * 自增id
     */
    private Long id;

    /**
     * 退款id
     */
    private Long refundid;

    /**
     * 已有支付id
     */
    private Long payid;

    /**
     * 金额
     */
    private Double money;

    /**
     * 付款方id
     */
    private Long payerid;

    /**
     * 收款方id
     */
    private Long payeeid;

    /**
     * 付款状态
     */
    private Integer status;

    /**
     * 支付时间
     */
    private Date date;

    /**
     * 退款信息备注
     */
    private String note;
}

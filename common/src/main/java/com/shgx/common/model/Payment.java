package com.shgx.common.model;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * @author: guangxush
 * @create: 2019/10/15
 */
@Data
@ToString
public class Payment {
    /**
     * 自增id
     */
    private Long id;

    /**
     * 支付id
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
     * 付款信息备注
     */
    private String note;
}

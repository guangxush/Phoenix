package com.shgx.producer.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * @author: guangxush
 * @create: 2019/10/15
 */
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "payment")
public class Payment {
    /**
     * 自增id
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 支付id
     */
    @Column(name = "payid")
    private Long payid;

    /**
     * 金额
     */
    @Column(name = "money")
    private double money;

    /**
     * 付款方id
     */
    @Column(name = "payerid")
    private Long payerid;

    /**
     * 收款方id
     */
    @Column(name = "payeeid")
    private Long payeeid;

    /**
     * 付款状态
     */
    @Column(name = "status")
    private Integer status;

    /**
     * 支付时间
     */
    @Column(name = "date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    /**
     * 付款信息备注
     */
    @Column(name = "note")
    private String note;
}

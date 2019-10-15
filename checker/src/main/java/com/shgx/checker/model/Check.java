package com.shgx.checker.model;

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
@Table(name = "check")
public class Check {
    /**
     * 自增id
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 校验id
     */
    @Column(name = "checkid")
    private Long checkid;

    /**
     * 支付id
     */
    @Column(name = "payid")
    private Long payid;

    /**
     * 退款id
     */
    @Column(name = "refundid")
    private Long refundid;

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

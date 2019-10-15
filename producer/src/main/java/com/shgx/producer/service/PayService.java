package com.shgx.producer.service;

import com.shgx.producer.model.Payment;

/**
 * @author: guangxush
 * @create: 2019/10/15
 */
public interface PayService {
    /**
     * 查询支付信息
     * @param id
     * @return
     */
    Payment queryPayment(Long id);

    /**
     * 保存支付信息
     * @param payment
     * @return
     */
    Boolean savePayment(Payment payment);

    /**
     * 更新信息
     * @param payment
     * @return
     */
    Boolean updatePayment(Payment payment);
}

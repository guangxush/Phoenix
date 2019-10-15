package com.shgx.producer.service;


import com.shgx.producer.model.Refundment;

/**
 * @author: guangxush
 * @create: 2019/10/15
 */
public interface RefundService {
    /**
     * 查询退款信息
     *
     * @param id
     * @return
     */
    Refundment queryRefundment(Long id);

    /**
     * 退款
     *
     * @param refundment
     * @return
     */
    Boolean saveRefundment(Refundment refundment);

    /**
     * 更新退款信息
     *
     * @param refundment
     * @return
     */
    Boolean updateRefundment(Refundment refundment);
}

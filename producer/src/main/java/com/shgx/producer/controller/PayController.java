package com.shgx.producer.controller;

import com.shgx.common.model.ApiResponse;
import com.shgx.producer.model.Payment;
import com.shgx.producer.service.PayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author: guangxush
 * @create: 2019/10/15
 */
@RestController
@Slf4j
@RequestMapping(path = "/pay")
public class PayController {

    @Autowired
    private PayService payService;

    @RequestMapping(path = "/query/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ApiResponse<Payment> query(@PathVariable("id") Long id) {
        if (id == null) {
            return new ApiResponse<Payment>().fail(new Payment());
        }
        Payment payment = payService.queryPayment(id);
        return new ApiResponse<Payment>().success(payment);
    }

    /**
     * 支付
     *
     * @param payment
     * @return
     */
    @RequestMapping(path = "/insert", method = RequestMethod.POST)
    @ResponseBody
    public ApiResponse<Boolean> insert(@RequestBody Payment payment) {
        try {
            Boolean result = payService.savePayment(payment);
            return new ApiResponse<Boolean>().success(result);
        } catch (InternalError error) {
            log.error("insert error");
        }
        return null;
    }


    /**
     * 修改支付信息
     *
     * @param payment
     * @return
     */
    @RequestMapping(path = "/update", method = RequestMethod.POST)
    @ResponseBody
    public ApiResponse<Boolean> update(@RequestBody Payment payment) {
        try {
            Boolean result = payService.updatePayment(payment);
            return new ApiResponse<Boolean>().success(result);
        } catch (InternalError error) {
            log.error("update error");
        }
        return null;
    }
}

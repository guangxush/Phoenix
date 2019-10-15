package com.shgx.producer.controller;

import com.shgx.common.model.ApiResponse;
import com.shgx.producer.model.Refundment;
import com.shgx.producer.service.RefundService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author: guangxush
 * @create: 2019/10/15
 */
@RestController
@Slf4j
@RequestMapping(path = "/refund")
public class RefundController {

    @Autowired
    private RefundService refundService;

    @RequestMapping(path = "/query/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ApiResponse<Refundment> query(@PathVariable("id") Long id) {
        if (id == null) {
            return new ApiResponse<Refundment>().fail(new Refundment());
        }
        Refundment refundment = refundService.queryRefundment(id);
        return new ApiResponse<Refundment>().success(refundment);
    }

    /**
     * 退款
     *
     * @param refundment
     * @return
     */
    @RequestMapping(path = "/insert", method = RequestMethod.POST)
    @ResponseBody
    public ApiResponse<Boolean> insert(@RequestBody Refundment refundment) {
        try {
            Boolean result = refundService.saveRefundment(refundment);
            return new ApiResponse<Boolean>().success(result);
        } catch (InternalError error) {
            log.error("insert error");
        }
        return null;
    }


    /**
     * 修改退款信息
     *
     * @param refundment
     * @return
     */
    @RequestMapping(path = "/update", method = RequestMethod.POST)
    @ResponseBody
    public ApiResponse<Boolean> update(@RequestBody Refundment refundment) {
        try {
            Boolean result = refundService.updateRefundment(refundment);
            return new ApiResponse<Boolean>().success(result);
        } catch (InternalError error) {
            log.error("update error");
        }
        return null;
    }
}

package com.shgx.checker.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.shgx.checker.model.Check;
import com.shgx.checker.service.CheckInfoService;
import com.shgx.checker.service.CheckService;
import com.shgx.common.model.Payment;
import com.shgx.common.model.Refundment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Random;

/**
 * @author: guangxush
 * @create: 2019/10/15
 */
@Service
@Slf4j
public class RefundCheckServiceImpl implements CheckService {

    @Autowired
    private RefundQueryServiceImpl queryService;

    @Autowired
    private CheckInfoService checkInfoService;

    @Override
    public Boolean doAccept(Object object) {
        if (object instanceof Refundment) {
            return true;
        }
        return false;
    }

    @Override
    public Boolean doCheck(Object object) {
        return doAccept(object) && checker((Refundment) object);
    }

    private Boolean checker(Refundment refundment) {
        Long payid = refundment.getPayid();
        Object object = queryService.doQuery(payid.toString());
        if(object == null){
            log.info("查询失败，忽略核对！");
            return true;
        }
        Payment payment = new Payment();
        try{
            String result = new JSONObject((LinkedHashMap)object).toString();
            Object object1 = JSON.parseObject(result, new TypeReference<Payment>() {});
            BeanUtils.copyProperties(object1, payment);
        }catch (Exception e){
            String info = "类型转换异常";
            return checkErrorProcess(info, refundment, payment);
        }

        if (refundment.getPayerid().longValue() != payment.getPayerid().longValue()) {
            String info = MessageFormat.format("{0}的付款方不一致,refund付款方为{1}, pay付款方为{2}！", refundment.getPayid(), refundment.getPayerid(), payment.getPayerid());
            return checkErrorProcess(info, refundment, payment);
        }

        if (refundment.getPayeeid().longValue() != payment.getPayeeid().longValue()) {
            String info = MessageFormat.format("{0}的收款方不一致,refund收款方为{1}, pay收款方为{2}！", refundment.getPayid(), refundment.getPayeeid(), payment.getPayeeid());
            return checkErrorProcess(info, refundment, payment);
        }

        if (refundment.getMoney().doubleValue() != payment.getMoney().doubleValue()) {
            String info = MessageFormat.format("{0}的退款金额{1}和转账金额{2}不一致！", refundment.getPayid(), refundment.getMoney().doubleValue(), payment.getMoney().doubleValue());
            return checkErrorProcess(info, refundment, payment);
        }
        return true;
    }

    public Boolean checkErrorProcess(String info, Refundment refundment, Payment payment) {
        log.error(info);
        saveCheckInfo(info, refundment, payment);
        return false;
    }

    private Boolean saveCheckInfo(String info, Refundment refundment, Payment payment) {
        Check check = Check.builder()
                .checkid(Long.valueOf(new Random().nextInt()))
                .payid(payment.getPayid())
                .refundid(refundment.getRefundid())
                .date(new Date())
                .status(1)
                .note(info)
                .build();
        return checkInfoService.saveCheck(check);
    }
}

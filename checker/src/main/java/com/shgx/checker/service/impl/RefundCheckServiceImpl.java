package com.shgx.checker.service.impl;

import com.shgx.checker.model.Check;
import com.shgx.checker.service.CheckInfoService;
import com.shgx.checker.service.CheckService;
import com.shgx.common.model.Payment;
import com.shgx.common.model.Refundment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

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
        BeanUtils.copyProperties(object, payment);

        if(payment.getPayerid() == null){
            return true;
        }

        if (refundment.getPayerid().longValue() != payment.getPayerid().longValue()) {
            String info = String.format("{0}的付款方不一致！", refundment.getId());
            return checkErrorProcess(info, refundment, payment);
        }

        if (refundment.getPayeeid().longValue() != payment.getPayeeid().longValue()) {
            String info = String.format("{0}的收款方不一致！", refundment.getId());
            return checkErrorProcess(info, refundment, payment);
        }

        if (refundment.getMoney().doubleValue() != payment.getMoney().doubleValue()) {
            String info = String.format("{0}的退款金额{1}和转账金额{2}不一致！", refundment.getId(), refundment.getMoney().doubleValue(), payment.getMoney().doubleValue());
            return checkErrorProcess(info, refundment, payment);
        }
        return true;
    }

    private Boolean checkErrorProcess(String info, Refundment refundment, Payment payment) {
        log.error(info);
        saveCheckInfo(refundment, payment);
        return false;
    }

    private Boolean saveCheckInfo(Refundment refundment, Payment payment) {
        Check check = Check.builder()
                .payid(payment.getPayid())
                .refundid(refundment.getRefundid())
                .date(new Date())
                .status(1)
                .note("")
                .build();
        return checkInfoService.saveCheck(check);
    }
}

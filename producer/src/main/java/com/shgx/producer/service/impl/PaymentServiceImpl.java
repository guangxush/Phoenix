package com.shgx.producer.service.impl;

import com.shgx.producer.model.Payment;
import com.shgx.producer.repository.PaymentRepo;
import com.shgx.producer.service.PayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;


/**
 * @author: guangxush
 * @create: 2019/10/15
 */
@Service
@Slf4j
public class PaymentServiceImpl implements PayService {

    @Autowired
    private PaymentRepo paymentRepo;

    /**
     * 查询支付信息
     * @param id
     * @return
     */
    @Override
    public Payment queryPayment(Long id) {
        Optional<Payment> payment = paymentRepo.findByPayid(id);
        if(payment.isPresent()){
            return Payment.builder()
                    .id(id)
                    .payid(payment.get().getPayid())
                    .payerid(payment.get().getPayerid())
                    .payeeid(payment.get().getPayeeid())
                    .money(payment.get().getMoney())
                    .status(payment.get().getStatus())
                    .date(payment.get().getDate())
                    .note(payment.get().getNote())
                    .build();
        }
        return null;
    }

    /**
     * 保存支付信息
     * @param payment
     * @return
     */
    @Override
    public Boolean savePayment(Payment payment) {
        Optional<List<Payment>> paymentDB = paymentRepo.findAllById(payment.getId());
        if (paymentDB.isPresent()) {
            return true;
        }
        try {
            payment.setDate(new Date());
            save(payment);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 更新支付信息
     * @param payment
     * @return
     */
    @Override
    public Boolean updatePayment(Payment payment) {
        Optional<Payment> paymentDB = paymentRepo.findByPayid(payment.getId());
        if(paymentDB.isPresent() && payment!=null){
            Payment paymentTemp = paymentDB.get();
            paymentTemp.setMoney(payment.getMoney());
            paymentTemp.setPayerid(payment.getPayerid());
            paymentTemp.setPayeeid(payment.getPayeeid());
            paymentTemp.setStatus(payment.getStatus());
            paymentTemp.setNote(payment.getNote());
            paymentTemp.setDate(new Date());
            save(paymentTemp);
        }else{
            log.error("the {} is not in db!", payment.toString());
            return false;
        }
        return true;
    }

    private Payment save(Payment payment){
        payment = paymentRepo.save(payment);
        if(payment.getId()<=0){
            log.error("fail to save the payment:{}", payment.toString());
        }
        return payment;
    }
}

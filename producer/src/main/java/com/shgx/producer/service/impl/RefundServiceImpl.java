package com.shgx.producer.service.impl;

import com.shgx.producer.model.Payment;
import com.shgx.producer.model.Refundment;
import com.shgx.producer.repository.RefundmentRepo;
import com.shgx.producer.service.RefundService;
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
public class RefundServiceImpl implements RefundService {

    @Autowired
    private RefundmentRepo refundmentRepo;

    /**
     * 查询退款
     *
     * @param id
     * @return
     */
    @Override
    public Refundment queryRefundment(Long id) {
        Optional<Refundment> refundment = refundmentRepo.findByRefundid(id);
        if (refundment.isPresent()) {
            return Refundment.builder()
                    .id(id)
                    .payid(refundment.get().getPayid())
                    .refundid(refundment.get().getRefundid())
                    .payerid(refundment.get().getPayerid())
                    .payeeid(refundment.get().getPayeeid())
                    .money(refundment.get().getMoney())
                    .status(refundment.get().getStatus())
                    .date(refundment.get().getDate())
                    .note(refundment.get().getNote())
                    .build();
        }
        return null;
    }

    /**
     * 保存退款信息
     *
     * @param refundment
     * @return
     */
    @Override
    public Boolean saveRefundment(Refundment refundment) {
        Optional<List<Refundment>> refundmentDB = refundmentRepo.findAllById(refundment.getId());
        if (refundmentDB.isPresent()) {
            return true;
        }
        try {
            refundment.setDate(new Date());
            save(refundment);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 更新退款信息
     *
     * @param refundment
     * @return
     */
    @Override
    public Boolean updateRefundment(Refundment refundment) {
        Optional<Refundment> refundmentDB = refundmentRepo.findById(refundment.getId());
        // todo null
        if (refundmentDB.isPresent() && refundment != null) {
            Refundment refundmentTemp = refundmentDB.get();
            refundmentTemp.setMoney(refundment.getMoney());
            refundmentTemp.setPayid(refundment.getPayid());
            refundmentTemp.setPayerid(refundment.getPayerid());
            refundmentTemp.setPayeeid(refundment.getPayeeid());
            refundmentTemp.setStatus(refundment.getStatus());
            refundmentTemp.setNote(refundment.getNote());
            refundmentTemp.setDate(new Date());
            save(refundmentTemp);
        } else {
            log.error("the {} is not in db!", refundment.toString());
            return false;
        }
        return true;
    }

    private Refundment save(Refundment refundment) {
        refundment = refundmentRepo.save(refundment);
        if (refundment.getId() <= 0) {
            log.error("fail to save the refundment:{}", refundment.toString());
        }
        return refundment;
    }
}

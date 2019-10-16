package com.shgx.checker.service.impl;

import com.shgx.checker.model.Check;
import com.shgx.checker.repository.CheckRepo;
import com.shgx.checker.service.CheckInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

/**
 * @author: guangxush
 * @create: 2019/10/15
 */
@Service
@Slf4j
public class CheckInfoServiceImpl implements CheckInfoService {

    @Autowired
    private CheckRepo checkRepo;

    @Override
    public Check queryCheck(Long id) {
        Optional<Check> check = checkRepo.findByCheckid(id);
        if (check.isPresent()) {
            return Check.builder()
                    .id(id)
                    .checkid(check.get().getCheckid())
                    .payid(check.get().getPayid())
                    .refundid(check.get().getRefundid())
                    .date(new Date())
                    .status(check.get().getStatus())
                    .note(check.get().getNote())
                    .build();
        }
        return null;
    }

    @Override
    public Boolean saveCheck(Check check) {
        Optional<Check> checkDB = checkRepo.findByCheckid(check.getCheckid());
        if (checkDB.isPresent()) {
            return true;
        }
        try {
            check.setDate(new Date());
            save(check);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public Boolean updateCheck(Check check) {
        Optional<Check> checkDB = checkRepo.findByCheckid(check.getId());
        if (checkDB.isPresent() && check != null) {
            Check checkTemp = checkDB.get();
            checkTemp.setCheckid(check.getCheckid());
            checkTemp.setPayid(check.getPayid());
            checkTemp.setRefundid(check.getRefundid());
            checkTemp.setStatus(check.getStatus());
            checkTemp.setNote(check.getNote());
            checkTemp.setDate(new Date());
            save(checkTemp);
        } else {
            log.error("the {} is not in db!", check.toString());
            return false;
        }
        return true;
    }

    private Check save(Check check) {
        check = checkRepo.save(check);
        if (check.getId() <= 0) {
            log.error("fail to save the check:{}", check.toString());
        }
        return check;
    }
}

package com.shgx.checker.controller;

import com.shgx.checker.model.Check;
import com.shgx.checker.service.CheckInfoService;
import com.shgx.checker.service.CheckService;
import com.shgx.common.model.ApiResponse;
import com.shgx.common.model.Refundment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author: guangxush
 * @create: 2019/10/15
 */
@RestController
@Slf4j
public class CheckController {

    @Autowired
    private CheckInfoService checkInfoService;

    @Autowired
    private CheckService checkService;

    @RequestMapping(path = "/query/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ApiResponse<Check> query(@PathVariable("id") Long id) {
        if (id == null) {
            return new ApiResponse<Check>().fail(new Check());
        }
        Check check = checkInfoService.queryCheck(id);
        return new ApiResponse<Check>().success(check);
    }

    @RequestMapping(path = "/check", method = RequestMethod.POST)
    @ResponseBody
    public ApiResponse<Boolean> insert(@RequestBody Refundment refundment) {
        try {
            Boolean result = checkService.doCheck(refundment);
            return new ApiResponse<Boolean>().success(result);
        } catch (InternalError error) {
            log.error("insert error");
        }
        return null;
    }
}

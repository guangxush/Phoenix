package com.shgx.router.controller;

import com.shgx.common.model.ApiResponse;
import com.shgx.common.model.Refundment;
import com.shgx.router.service.RequestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author: guangxush
 * @create: 2020/02/20
 */
@RestController
@Slf4j
public class RouterController {

    @Autowired
    private RequestService requestService;

    String url = "http://localhost:8081/checker/check";

    @RequestMapping(path = "/check", method = RequestMethod.POST)
    @ResponseBody
    public ApiResponse<Boolean> insert(@RequestBody Refundment refundment) {
        try {
            Boolean result = requestService.jsonRequest(url, refundment);;
            return new ApiResponse<Boolean>().success(result);
        } catch (InternalError error) {
            log.error("check error");
        }
        return null;
    }
}

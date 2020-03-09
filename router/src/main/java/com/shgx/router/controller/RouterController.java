package com.shgx.router.controller;

import com.shgx.common.model.ApiResponse;
import com.shgx.common.model.Refundment;
import com.shgx.router.service.LoadBalance;
import com.shgx.router.service.RequestService;
import com.shgx.router.service.RouteService;
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

    @Autowired
    private RouteService routeService;

    @Autowired
    private LoadBalance loadBalance;

    String url = "http://localhost:8081/checker/check";

    @RequestMapping(path = "/check", method = RequestMethod.POST)
    @ResponseBody
    public ApiResponse<Boolean> forward(@RequestParam(value = "ports") String ports,
                                        @RequestParam(value = "services", required = false) String services,
                                        @RequestBody Refundment refundment) {
        try {
            url = getUrl(ports, services);
            Boolean result = requestService.jsonRequest(url, refundment);
            return new ApiResponse<Boolean>().success(result);
        } catch (InternalError error) {
            log.error("check error");
        }
        return null;
    }

    private String getUrl(String ports, String services) {
        String port = loadBalance.getMachine(ports);
        String service = routeService.getService(services);
        url = String.format("http://localhost:%s/checker/%s", port, service);
        return url;
    }
}

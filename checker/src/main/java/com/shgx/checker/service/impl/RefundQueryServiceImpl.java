package com.shgx.checker.service.impl;

import com.shgx.checker.service.QueryService;
import org.springframework.stereotype.Service;

/**
 * @author: guangxush
 * @create: 2019/10/15
 */
@Service
public class RefundQueryServiceImpl extends QueryService {

    private final String url = "http://localhost:8080/phoenix/pay/query/";

    @Override
    public Object doQuery(String param) {
        return paramRequest(url, param);
    }
}

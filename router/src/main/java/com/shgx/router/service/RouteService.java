package com.shgx.router.service;

import com.shgx.common.exception.InternalError;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * 路由到远程不同服务
 * @author: guangxush
 * @create: 2020/02/20
 */
@Service
public class RouteService {

    private Map<String, String> serviceMap = new HashMap<>();

    @PostConstruct
    public void init(){
        //这里简单模拟一下
        System.out.println("init the service serviceMap from database");
        serviceMap.put("check","check");
    }

    public String getService(String params){
        if(params==null||params.length()==0){
            throw new InternalError("invalid params");
        }
        if(serviceMap.get(params)==null){
            throw new InternalError("Illegal parameter");
        }
        return serviceMap.get(params);
    }
}

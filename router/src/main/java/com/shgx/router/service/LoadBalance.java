package com.shgx.router.service;

import com.shgx.common.exception.InternalError;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * 通过负载均衡调用
 * @author: guangxush
 * @create: 2020/02/20
 */
@Service
public class LoadBalance {
    private Map<String, String> machineMap = new HashMap<>();

    @PostConstruct
    public void init(){
        //这里简单模拟一下
        System.out.println("init the service machineMap from database");
        machineMap.put("8081","8081");
    }

    public String getMachine(String params){
        if(params==null||params.length()==0){
            throw new InternalError("invalid params");
        }
        if(machineMap.get(params)==null){
            throw new InternalError("Illegal parameter");
        }
        return machineMap.get(params);
    }
}

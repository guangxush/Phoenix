package com.shgx.checker.service;


/**
 * @author: guangxush
 * @create: 2019/10/15
 */
public interface CheckService {

    /**
     * 判断是否进行校验
     * @param object
     * @return
     */
    Boolean doAccept(Object object);

    /**
     * 返回校验结果
     * @param object
     * @return
     */
    Boolean doCheck(Object object);
}

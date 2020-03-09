package com.shgx.common.base;

/**
 * @author: guangxush
 * @create: 2020/03/09
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

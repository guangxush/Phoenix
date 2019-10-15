package com.shgx.checker.service;

import com.shgx.checker.model.Check;

/**
 * @author: guangxush
 * @create: 2019/10/15
 */
public interface CheckInfoService {
    /**
     * 查询信息
     *
     * @param id
     * @return
     */
    Check queryCheck(Long id);

    /**
     * 保存信息
     *
     * @param check
     * @return
     */
    Boolean saveCheck(Check check);

    /**
     * 更新信息
     *
     * @param check
     * @return
     */
    Boolean updateCheck(Check check);
}

package com.langsin.oa.service;


import com.langsin.oa.dto.UserBackendDto;

/**
 *
 * @Description:  登录接口
 * @Author: wyy
 * @Date: 2019-10-16
 **/
public interface LoginService {

    /**
     * 登录验证
     * @param username 用户名
     * @return
     */
    UserBackendDto login(String username);
}

package com.langsin.oa.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.langsin.oa.dto.UserBackendDto;
import com.langsin.oa.mapper.UserBackendMapper;
import com.langsin.oa.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @Description:  登录接口
 * @Author: wyy
 * @Date: 2019-10-11
 **/
@Service
public class LoginServiceImpl implements LoginService {

    private UserBackendMapper userBackendMapper;

    @Autowired
    public LoginServiceImpl(UserBackendMapper userBackendMapper) {
        this.userBackendMapper = userBackendMapper;
    }

    @Override
    public UserBackendDto login(String username) {
        return userBackendMapper.selectOne(new QueryWrapper<UserBackendDto>().eq("username",username));
    }
}


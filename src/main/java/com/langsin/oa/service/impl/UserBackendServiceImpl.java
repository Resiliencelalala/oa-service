package com.langsin.oa.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.langsin.oa.dto.UserBackendDto;
import com.langsin.oa.dto.UserBackendSingleDto;
import com.langsin.oa.dto.UserBackendStatusDto;
import com.langsin.oa.dto.UserRoleDto;
import com.langsin.oa.enums.UserStatusEnum;
import com.langsin.oa.foundation.PageModel;
import com.langsin.oa.foundation.Result;
import com.langsin.oa.mapper.UserBackendMapper;
import com.langsin.oa.mapper.UserRoleMapper;
import com.langsin.oa.service.UserBackendService;
import com.langsin.oa.utils.ShiroUtils;
import com.langsin.oa.foundation.PageModel;
import com.langsin.oa.foundation.Result;
import com.langsin.oa.mapper.UserRoleMapper;
import com.langsin.oa.utils.ShiroUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.SessionsSecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * @Description: 系统用户管理
 * @Author: wyy
 * @Date: 2019-10-17
 **/
@Service
public class UserBackendServiceImpl implements UserBackendService {


    @Autowired
    private UserBackendMapper userBackendMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;


    @Override
    public IPage<UserBackendDto> selectUserAndPage(PageModel pageModel, String realName) {
        Page<UserBackendDto> page = new Page<>(pageModel.getPageNum(), pageModel.getPageSize());
        return userBackendMapper.selectUserAndPage(page, realName);
    }

    @Override
    public List<UserBackendSingleDto> selectUserByPermission(String permissionCode,String roleCode) {
        return userBackendMapper.selectUserByPermission(permissionCode,roleCode);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result insert(UserBackendDto userBackendDto) {
        if (userBackendMapper.selectCount(new QueryWrapper<UserBackendDto>().eq("username", userBackendDto.getUsername())) > 0) {
            return Result.failed("账号已经存在!");
        }
        if (userBackendMapper.selectCount(new QueryWrapper<UserBackendDto>().lambda().eq(UserBackendDto::getPhone, userBackendDto.getPhone())) > 0) {
            return Result.failed("手机号已经存在!");
        }
        if (userBackendMapper.selectCount(new QueryWrapper<UserBackendDto>().lambda().eq(UserBackendDto::getEmail, userBackendDto.getEmail())) > 0) {
            return Result.failed("邮箱已经存在!");
        }
        String salt = ShiroUtils.generateSalt();
        userBackendDto.setSalt(salt);
        userBackendDto.setPassword(ShiroUtils.sha256(userBackendDto.getPassword(), salt, 1024));
        userBackendDto.setCreateTime(LocalDateTime.now());
        int count = userBackendMapper.insert(userBackendDto);
        if (Objects.nonNull(userBackendDto.getRoleIdList())) {
            List<Long> temp = userBackendDto.getRoleIdList();
            userRoleMapper.insertList(userBackendDto.getId(),temp);
        }
        if (count > 0) {
            return Result.success("添加成功");
        }
        return Result.failed("发生未知错误,添加失败!");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result update(UserBackendDto userBackendDto) {
        //如果被禁用的用户在线 则需要强制下线
        if (UserStatusEnum.DELETED.getOrdinal().equals(userBackendDto.getStatus())) {
            forceLogout(userBackendDto.getId());
        }
        userBackendDto.setUpdateTime(LocalDateTime.now());
        int count = userBackendMapper.updateByUserId(userBackendDto);
        userRoleMapper.delByUserId(userBackendDto.getId());
        if (Objects.nonNull(userBackendDto.getRoleIdList())) {
            userRoleMapper.insertList(userBackendDto.getId(), userBackendDto.getRoleIdList());
        }
        if (count > 0) {
            return Result.success("修改成功");
        }
        return Result.failed("发生未知错误,修改失败!");
    }

    @Override
    public int delete(Long id) {
        forceLogout(id);
        int count = userBackendMapper.deleteById(id);
        userRoleMapper.delByUserId(id);
        return count;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateUserByStatus(UserBackendStatusDto userBackendStatusDto) {
        //如果被禁用的用户在线 则需要强制下线
        if (UserStatusEnum.DELETED.getOrdinal().equals(userBackendStatusDto.getStatus())) {
            forceLogout(userBackendStatusDto.getUserId());
        }
        return userBackendMapper.updateUserByStatus(userBackendStatusDto);
    }
    //强制下线
    public void forceLogout(Long userId){
        UserBackendDto userBackendDto = userBackendMapper.selectById(userId);
        Assert.notNull(userBackendDto,"用户不存在或者用户id错误");
        SessionsSecurityManager securityManager = (SessionsSecurityManager) SecurityUtils.getSecurityManager();
        DefaultSessionManager sessionManager = (DefaultSessionManager) securityManager.getSessionManager();
        Collection<Session> sessions = sessionManager.getSessionDAO().getActiveSessions();//获取当前已登录的用户session列表
        SimplePrincipalCollection principalCollection = new SimplePrincipalCollection();
        for (Session s : sessions) {
            principalCollection = (SimplePrincipalCollection) s.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
            UserBackendDto user = (UserBackendDto) principalCollection.getPrimaryPrincipal();
            if (user != null && userBackendDto.getUsername().equals(user.getUsername())) {
                sessionManager.getSessionDAO().delete(s);
            }
        }
    }
}

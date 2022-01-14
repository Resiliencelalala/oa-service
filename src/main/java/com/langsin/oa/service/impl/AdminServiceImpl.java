package com.langsin.oa.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.langsin.oa.auto.SecurityProperties;
import com.langsin.oa.dto.*;
import com.langsin.oa.foundation.Result;
import com.langsin.oa.mapper.PermissionMapper;
import com.langsin.oa.mapper.RoleMapper;
import com.langsin.oa.mapper.RolePermissionMapper;
import com.langsin.oa.mapper.UserBackendMapper;
import com.langsin.oa.service.AdminService;
import com.langsin.oa.utils.Base64Utils;
import com.langsin.oa.utils.RSAUtils;
import com.langsin.oa.utils.ShiroUtils;
import com.langsin.oa.utils.VideoUtil;
import com.langsin.oa.foundation.Result;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.SessionsSecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 *
 * @Description:  系统配置类
 * @Author: wyy
 * @Date: 2019-10-16
 **/
@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private UserBackendMapper userBackendMapper;

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public List<RoleDto> selectUserInfo() {
        UserBackendDto userBackendDto = (UserBackendDto)ShiroUtils.getSubject().getPrincipal();
        return roleMapper.selectByUserId(userBackendDto.getId());
    }

    @Override
    public List<MenuRouterDto> selectMenuTree() {
        UserBackendDto userBackendDto = (UserBackendDto)ShiroUtils.getSubject().getPrincipal();
        Long userId = userBackendDto.getId();
        List<Long> menuIdList = null;
        if (Objects.nonNull(userId)) {
            menuIdList = rolePermissionMapper.selectPermissionIdByUserId(userId);
        }
        if (menuIdList.size() == 0) {
            return null;
        }
        List<PermissionDto> permissionDtos = new ArrayList<>();
        List<PermissionDto> menus = permissionMapper.selectList(new QueryWrapper<PermissionDto>().in("id",menuIdList));
        if (Objects.isNull(menus)) {
            return null;
        }
        menus.forEach(menu -> {
            if (menu.getParentId() == null || menu.getParentId() == 0) {
                menu.setLevel(0);
                if (VideoUtil.exists(permissionDtos, menu)) {
                    permissionDtos.add(menu);
                }
            }
        });
        permissionDtos.sort((o1, o2) -> o1.getSort().compareTo(o2.getSort()));
        VideoUtil.findChildren(permissionDtos, menus, 0);

        List<MenuRouterDto> menuVos = VideoUtil.buildMenus(permissionDtos);
        List<PermissionDto> permsHidden = permissionMapper.selectPermsButtonByUserId(userId);
        MenuRouterDto menuVosHidden ;
        if (permsHidden.size() > 0) {
             menuVosHidden = VideoUtil.buildHiddenMenus(permsHidden);
            menuVos.add(menuVosHidden);
        }

        return menuVos;
    }

    @Override
    public Set<String> selectPermsCodeByUserId() {
        UserBackendDto userBackendDto = (UserBackendDto)ShiroUtils.getSubject().getPrincipal();
        Set<String> perms = new HashSet<>();
        List<PermissionDto> permissionDtos = permissionMapper.selectPermsCodeByUserId(userBackendDto.getId());
        if (Objects.isNull(permissionDtos)) {
            return null;
        }
        for(PermissionDto permissionDto:permissionDtos) {
            perms.add(permissionDto.getCode());
        }
        return perms;
    }

    @Override
    public Set<String> selectPermsCodeByUserIdAndShiro(Long userId) {
        Set<String> perms = new HashSet<>();
        List<PermissionDto> permissionDtos = permissionMapper.selectPermsCodeByUserId(userId);
        if (Objects.isNull(permissionDtos)) {
            return null;
        }
        for(PermissionDto permissionDto:permissionDtos) {
            perms.add(permissionDto.getCode());
        }
        return perms;
    }

    @Override
    public Set<String> selectRoleNameByUserIdAndShiro(Long userId) {
        List<RoleDto> roleDtos = roleMapper.selectByUserId(userId);
        if (Objects.isNull(roleDtos)) {
            return null;
        }
        Set<String> roleName = new HashSet<>();
        for(RoleDto roleDto:roleDtos) {
            roleName.add(roleDto.getName());
        }
        return roleName;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result updateUserPassword(UserPasswordDto userPasswordDto) {
        //当前登录用户session
        Session session = ShiroUtils.getSession();
        //当前登录用户
        UserBackendDto userBackendDto = (UserBackendDto)ShiroUtils.getSubject().getPrincipal();

        SessionsSecurityManager securityManager = (SessionsSecurityManager) SecurityUtils.getSecurityManager();
        DefaultSessionManager sessionManager = (DefaultSessionManager) securityManager.getSessionManager();
        Collection<Session> sessions = sessionManager.getSessionDAO().getActiveSessions();//获取当前已登录的用户session列表
        SimplePrincipalCollection principalCollection = new SimplePrincipalCollection();
        for (Session s : sessions) {
            if(!s.getId().equals(session.getId())){
                principalCollection = (SimplePrincipalCollection) s.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
                UserBackendDto user=(UserBackendDto)principalCollection.getPrimaryPrincipal();
                if (user != null && userBackendDto.getUsername().equals(user.getUsername())) {
                    sessionManager.getSessionDAO().delete(s);
                }
            }

        }

        if (Objects.isNull(userBackendDto)) {
            return Result.failed("未查询到个人信息,请刷新");
        }
        //密码解密
        String oldPassword = null;
        String newPassword = null;
        try {
            oldPassword = new String(RSAUtils.decryptByPrivateKey(Base64Utils.decode(userPasswordDto.getOldPassword()), securityProperties.getPrivateKey()), "UTF-8");
            newPassword = new String(RSAUtils.decryptByPrivateKey(Base64Utils.decode(userPasswordDto.getNewPassword()), securityProperties.getPrivateKey()), "UTF-8");
        } catch (Exception e) {
            return Result.failed("密码解密失败");
        }
        if(oldPassword.equals(newPassword)){
            return Result.failed("新密码和原密码不可相同");
        }
        if (!Objects.equals(userBackendDto.getPassword(), ShiroUtils.sha256(oldPassword, userBackendDto.getSalt(), 1024))) {
            return Result.failed("原密码不正确");
        }
        String salt = ShiroUtils.generateSalt();
        String password = ShiroUtils.sha256(newPassword, salt, 1024);
        int count = userBackendMapper.updateUserByPassword(userBackendDto.getId(), password, salt);
        return count > 0 ? Result.success(): Result.failed("修改错误");
    }

}

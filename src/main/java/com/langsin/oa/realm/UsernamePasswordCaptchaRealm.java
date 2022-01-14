package com.langsin.oa.realm;

import com.langsin.oa.dto.UserBackendDto;
import com.langsin.oa.service.AdminService;
import com.langsin.oa.service.LoginService;
import com.langsin.oa.service.AdminService;
import com.langsin.oa.service.LoginService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;
import java.util.Set;

/**
 * @author wyy
 * @date 19-2-7 上午10:09
 */
public class UsernamePasswordCaptchaRealm extends AuthorizingRealm {

    @Autowired
    private LoginService loginService;
    @Autowired
    private AdminService adminService;
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        UserBackendDto userBackendDto = (UserBackendDto) principalCollection.getPrimaryPrincipal();
        // 用户权限列表，根据用户拥有的权限标识与如 @permission标注的接口对比，决定是否可以调用接口
        Set<String> permissions = adminService.selectPermsCodeByUserIdAndShiro(userBackendDto.getId());
        Set<String> roleName = adminService.selectRoleNameByUserIdAndShiro(userBackendDto.getId());
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.addStringPermissions(permissions);
        simpleAuthorizationInfo.addRoles(roleName);
        return simpleAuthorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String userName = (String) authenticationToken.getPrincipal();
        UserBackendDto userBackendDto = loginService.login(userName);
        if (Objects.isNull(userBackendDto)) {
            throw new UnknownAccountException();
        }
        if (userBackendDto.getStatus()==0) {
            throw new DisabledAccountException("该账号已被禁用");
        }
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(userBackendDto, userBackendDto.getPassword(), ByteSource.Util.bytes(userBackendDto.getSalt()), getName());
        super.clearCachedAuthenticationInfo(simpleAuthenticationInfo.getPrincipals());
        return simpleAuthenticationInfo;
    }
}

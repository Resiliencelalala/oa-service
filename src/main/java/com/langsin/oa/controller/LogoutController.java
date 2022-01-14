package com.langsin.oa.controller;

import com.langsin.oa.dto.UserBackendDto;
import com.langsin.oa.foundation.Result;
import com.langsin.oa.realm.UsernamePasswordCaptchaRealm;
import com.langsin.oa.foundation.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Api(tags = {"退出"})
@RestController
@RequestMapping("/logout")
public class LogoutController {

    @Autowired
    private UsernamePasswordCaptchaRealm usernamePasswordCaptchaRealm;

    /**
     * 登出
     * @return
     */
    @ApiOperation("登出接口")
    @GetMapping
    public Result logout() {
        try {
            Subject subject = SecurityUtils.getSubject();
            UserBackendDto principal = (UserBackendDto) subject.getPrincipal();
            subject.logout();
            if (principal!=null) {
                Cache<Object, AuthorizationInfo> authorizationCache = usernamePasswordCaptchaRealm.getAuthorizationCache();
                if (authorizationCache!=null) {
                    authorizationCache.remove(principal.getUsername());
                }
            }
            return Result.success();
        }catch(Exception ee){
            ee.printStackTrace();
            return Result.failed("服务器异常!");
        }
    }
}

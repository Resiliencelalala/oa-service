package com.langsin.oa.controller;

import com.langsin.oa.annotation.DoValid;
import com.langsin.oa.auto.SecurityProperties;
import com.langsin.oa.constant.ConstantsPool;
import com.langsin.oa.dto.UserBackendDto;
import com.langsin.oa.dto.loginModels.LoginParam;
import com.langsin.oa.foundation.Result;
import com.langsin.oa.utils.Base64Utils;
import com.langsin.oa.utils.RSAUtils;
import com.langsin.oa.utils.ShiroUtils;
import com.langsin.oa.annotation.DoValid;
import com.langsin.oa.constant.ConstantsPool;
import com.langsin.oa.foundation.Result;
import com.langsin.oa.utils.Base64Utils;
import com.langsin.oa.utils.RSAUtils;
import com.langsin.oa.utils.ShiroUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;


/**
 * @author wyy
 * @date 2019/10/16 9:07
 */
@Api(tags = {"登录"})
@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private SecurityProperties securityProperties;
    /**
     * 登录接口
     * @param loginParam 登录信息
     * @return
     */
    @ApiOperation("登录接口")
    @PostMapping
    @DoValid
    public Result login(@RequestBody @Valid LoginParam loginParam, BindingResult result) {
        String username = loginParam.getUsername();
        //添加用户认证信息
        Subject subject = ShiroUtils.getSubject();

        //进行验证，这里可以捕获异常，然后返回对应信息
        try {
            //密码解密
            String pass = new String(RSAUtils.decryptByPrivateKey(Base64Utils.decode(loginParam.getPassword()), securityProperties.getPrivateKey()), "UTF-8");
            UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username, pass);
            subject.login(usernamePasswordToken);
            UserBackendDto principal = (UserBackendDto) subject.getPrincipal();
            Map<String, String> map = new HashMap<>();
            map.put("token", subject.getSession().getId().toString());
            map.put("username", principal.getUsername());
            map.put("id", principal.getId().toString());
            map.put("realName", principal.getRealName());
            //ajax登陆成功后直接返回成功即可 让前台判断跳往那个页面
            return Result.success("登录成功",map);
        } catch (IncorrectCredentialsException e) {
            e.printStackTrace();
            return Result.failed(e.getMessage());
        } catch (UnknownAccountException e){
            return Result.failed("账号不存在！");
        }catch (DisabledAccountException e) {
            return  Result.failed(e.getMessage());
        } catch (ExcessiveAttemptsException e){
            return  Result.failed("登录错误次数超过三次，请五分钟后重试！");
        }catch (Exception e) {
            //用户有可能已登陆 其他错误网络异常
            if (subject.isAuthenticated()) {
                return Result.success("登录成功",null);
            } else {
                return Result.failed(ConstantsPool.EXCEPTION_NETWORK_ERROR);
            }
        }
    }
}

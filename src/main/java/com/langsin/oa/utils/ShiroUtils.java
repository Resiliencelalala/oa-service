package com.langsin.oa.utils;

import com.langsin.oa.dto.UserBackendDto;
import com.langsin.oa.foundation.Result;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;


/**
 * shiro的md5以及散列
 *
 * @author wangyy
 * @date 19-2-7 上午9:04
 */
public class ShiroUtils {

    /**
     * shiro自带md5加密
     *
     * @param message        要加密的信息
     * @param salt           盐值
     * @param hashIterations 散列次数 比如2次:messageDigest(messageDigest(xxx))
     * @return string
     */
    public static String messageDigest(String message, String salt, int hashIterations) {
        return new SimpleHash("md5", message, salt, hashIterations).toString();
    }

    /**
     * sha256散列加密
     *
     * @param message        要加密的信息
     * @param salt           盐值
     * @param hashIterations 散列次数
     * @return String
     */
    public static String sha256(String message, String salt, int hashIterations) {
        return new SimpleHash("SHA-256", message, salt, hashIterations).toString();
    }

    /**
     * 获取随机盐值
     */
    public static String generateSalt() {
        return new SecureRandomNumberGenerator().nextBytes().toHex();
    }

    /**
     * 通过shiro获取session
     *
     * @return 被shiro包装过的session
     */
    public static Session getSession() {
        return SecurityUtils.getSubject().getSession();
    }

    /**
     * 获取主体对象
     *
     * @return Subject
     */
    public static Subject getSubject() {
        return SecurityUtils.getSubject();
    }

    /**
     * 获取当前登录对象
     * @return
     */
    public static LoginInfo getCurrentUserInfo(){
        LoginInfo res = null;
        try {
            res = new LoginInfo();
            Subject subject = ShiroUtils.getSubject();
            UserBackendDto principal = (UserBackendDto) subject.getPrincipal();
            res.setUserId(principal.getId().toString());
            res.setUserName(principal.getRealName());
            return res;
        }catch (Exception ex){
            return null;
        }
    }

    public static class LoginInfo{
        private String userName;

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        private String userId;
    }
}

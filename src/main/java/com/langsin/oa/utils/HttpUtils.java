package com.langsin.oa.utils;

import com.langsin.oa.constant.ConstantsPool;
import com.langsin.oa.foundation.Result;
import com.langsin.oa.constant.ConstantsPool;
import com.langsin.oa.foundation.Result;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Objects;

/**
 * 请求工具类
 *
 * @author suiguozhen
 * @date 19/03/01 8:46
 */
public class HttpUtils {
    private static final String HEADER_REAL_IP = "X-Real-IP";
    private static final String UNKNOWN = "unknown";
    private static final String SLASH = "../";
    private static final String BACKSLASH = "..\\";
    private static final String HEADER_FORWARDED_FOR = "X-Forwarded-For";
    private static final String COMMA = ",";
    private static final String IP_EMPTY = "0:0:0:0:0:0:0:1";
    private static final String IP_LOOP = "127.0.0.1";
    private static final Logger logger = LoggerFactory.getLogger(HttpUtils.class);


    /**
     * 获取request对象
     *
     * @return HttpServletRequest
     */
    public static HttpServletRequest getRequest() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return requestAttributes.getRequest();
    }

    /**
     * 获取头信息
     *
     * @param header 头信息
     * @return string
     */
    public static String getHeader(String header) {
        HttpServletRequest request = getRequest();
        return request.getHeader(header);
    }

    /**
     * 获取response对象
     *
     * @return HttpServletResponse
     */
    public static HttpServletResponse getResponse() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return requestAttributes.getResponse();
    }

    /**
     * 响应异常处理 会区分是ajax请求和页面请求 进行重定向或直接返回json数据
     *
     * @param info 错误信息 针对json数据
     * @param path 重定向要跳转的路径
     * @return result
     */
    public static Result responseExceptionHandler(String info, String path) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        if (Objects.isNull(request.getHeader(ConstantsPool.HEADER_X_REQUESTED_WITH))) {
            HttpServletResponse response = requestAttributes.getResponse();
            try {
                response.sendRedirect(request.getServletPath() + path);
                return null;
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
        }
        return Result.failed(info);
    }

    /**
     * 获取当前session
     *
     * @return HttpSession
     */
    public static HttpSession getSession() {
        return getRequest().getSession();
    }

    /**
     * 获取访问者IP
     * 在一般情况下使用Request.getRemoteAddress()即可，但是经过nginx等反向代理软件后，这个方法会失效。
     * 本方法先从Header中获取X-Real-IP，如果不存在再从X-Forwarded-For获得第一个IP(用,分割)，
     * 如果还不存在则调用Request .getRemoteAddress()。
     *
     * @return string
     */
    public static String getRemoteAddress() {
        HttpServletRequest request = getRequest();
        String ip = request.getHeader(HEADER_REAL_IP);
        if (!StringUtils.isBlank(ip) && !UNKNOWN.equalsIgnoreCase(ip)) {
            if (ip.contains(SLASH) || ip.contains(BACKSLASH)) {
                return "";
            }
            return ip;
        }
        ip = request.getHeader(HEADER_FORWARDED_FOR);
        if (!StringUtils.isBlank(ip) && !UNKNOWN.equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个IP值，第一个为真实IP。
            int index = ip.indexOf(COMMA);
            if (index != -1) {
                ip = ip.substring(0, index);
            }
            if (ip.contains(SLASH) || ip.contains(BACKSLASH)) {
                return "";
            }
            return ip;
        } else {
            ip = request.getRemoteAddr();
            if (ip.contains(SLASH) || ip.contains(BACKSLASH)) {
                return "";
            }
            if (ip.equals(IP_EMPTY)) {
                ip = IP_LOOP;
            }
            return ip;
        }
    }
}

package com.langsin.oa.constant;

/**
 * 常量池 包含以下内容:
 * 英文、正则等信息 不含有中文信息
 *
 * @author wyy
 * @date 19/01/19 16:23
 */
public class ConstantsPool {

    private ConstantsPool(){}

    /**
     * 异常相关
     */
    public static final String EXCEPTION_NETWORK_ERROR = "网络繁忙，请稍后重试";
    public static final String EXCEPTION_NO_PERMISSION = "您无权进行此操作,请联系管理员";

    /**
     * 请求头其他相关
     * HEADER_CONTENT_TYPE: json请求
     * HEADER_X_REQUESTED_WITH: ajax请求头
     * HEADER_XML_HTTP_REQUEST: ajax请求值
     * HEADER_AUTHORIZATION:  前端请求携带authorization验证token
     */
    public static final String HEADER_AUTHORIZATION = "authorization";
    public static final String HEADER_XML_HTTP_REQUEST = "XMLHttpRequest";
    public static final String HEADER_X_REQUESTED_WITH = "x-requested-with";
    public static final String HEADER_CONTENT_TYPE = "application/json; charset=UTF-8";

    /**
     * 正则表达式
     * PHONE_PATTERN：匹配手机号
     * PASSWORD_PATTERN：匹配密码
     */
    public static final String REGEXP_PHONE_PATTERN = "^1([38]\\d|5[0-35-9]|7[3678])\\d{8}$";
    public static final String REGEXP_PASSWORD_PATTERN = "^(?![A-Za-z]+$)(?!\\d+$)(?![\\W_]+$).{6,20}$";

    /**
     * 14天免登陆生成cookis 的 key
     */
    public static final String  SECURITY_SHA = "SHA-256";

    /**
     * 验证码有效期（分钟）
     */
    public static final Integer CAPTCHA_EXPIRATION = 2;

    //xls.or.doc 头文件信息
    public static final String DOC_XLS ="D0CF11E0";

    //xlsx.or.docx 头文件信息
    public static final String DOCX_XLSX ="504B0304";

    //pdf 头文件信息
    public static final String PDF ="25504446";



}

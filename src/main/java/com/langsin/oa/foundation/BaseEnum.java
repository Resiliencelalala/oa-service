package com.langsin.oa.foundation;

/**
 * 枚举父类 统一实现接口
 *
 * @author suiguozhen
 */
public interface BaseEnum {

    /**
     * 获得标号 默认返回标号 rest请求时
     *
     * @return integer
     */
    Integer getOrdinal();

    /**
     * 获取label
     *
     * @return string
     */
    String getLabel();
}

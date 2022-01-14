package com.langsin.oa.foundation;

/**
 * 状态码枚举
 *
 * @author wyy
 */
public enum RestCodeEnum implements BaseEnum {
    /***操作成功*/
    SUCCESS(200, "操作成功"),
    /*** 登陆失效*/
    NOT_LOGIN(401, "未登录"),
    /*** 未知错误*/
    ERROR(500, "错误");
    private Integer ordinal;
    private String label;

    RestCodeEnum(Integer ordinal, String label) {
        this.ordinal = ordinal;
        this.label = label;
    }

    @Override
    public Integer getOrdinal() {
        return ordinal;
    }

    @Override
    public String getLabel() {
        return label;
    }

    public void setOrdinal(Integer ordinal) {
        this.ordinal = ordinal;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
package com.langsin.oa.enums;

import com.langsin.oa.foundation.BaseEnum;
import com.langsin.oa.foundation.BaseEnum;
import lombok.Getter;

/**
 * @author wyy
 * @version 1.0
 * @Description: 用户添加
 * @date 2019/9/30 16:52
 */
@Getter
public enum UserStatusEnum implements BaseEnum {
    DELETED(0,"禁用"),
    NORMAL(2,"启用"),
    DEL(1,"删除");

    private Integer ordinal;
    private String label;

    UserStatusEnum(Integer ordinal, String label){
        this.ordinal = ordinal;
        this.label = label;
    }
}

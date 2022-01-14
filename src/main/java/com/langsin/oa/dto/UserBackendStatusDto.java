package com.langsin.oa.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserBackendStatusDto {

    @ApiModelProperty(value = "用户id")
    private Long userId;

    /**
     * 状态( 0:禁用 1:启用)
     */
    @ApiModelProperty(value = "状态( 0:禁用 1:启用)")
    private Integer status;
}

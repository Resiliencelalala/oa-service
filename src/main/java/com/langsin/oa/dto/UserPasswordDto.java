package com.langsin.oa.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author wyy
 * @date 2019/10/25
 */
@Data
public class UserPasswordDto {

    @ApiModelProperty(value = "用户id")
    private Long id;

    @ApiModelProperty(value = "旧密码")
    private String oldPassword;

    @ApiModelProperty(value = "新密码")
    private String newPassword;
}

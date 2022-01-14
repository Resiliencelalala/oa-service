package com.langsin.oa.dto.loginModels;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


/**
 * @author wyy
 * @date 2019/10/12 9:33
 */
@Data
public class LoginParam {

    /**
     * 手机号
     */
    @ApiModelProperty(value = "用户名")
    @NotNull(message = "用户名不能为空")
    @NotEmpty(message = "用户名不能为空")
    private String username;

    /**
     * 密码
     */
    @ApiModelProperty(value = "密码")
    @NotNull(message = "密码不能为空")
    @NotEmpty(message = "密码不能为空")
    private String password;
}

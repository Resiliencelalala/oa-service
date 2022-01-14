package com.langsin.oa.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.langsin.oa.foundation.BaseDto;
import com.langsin.oa.foundation.BaseDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author wyy
 * @date 2019/10/11
 */
@Data
@TableName(value = "r_user_backend")
public class UserBackendDto extends BaseDto {

    @ApiModelProperty(value = "账号")
    @NotNull(message = "账号不能为空")
    @Size(min = 4,max = 15,message = "账号长度为4-15个字符")
    private String username;

    @ApiModelProperty(value = "真实姓名")
    @NotNull(message = "真实姓名不能为空")
    private String realName;

    @ApiModelProperty(value = "联系电话")
    @NotNull(message = "联系电话不能为空")
    private String phone;

    @ApiModelProperty(value = "邮箱")
    @NotNull(message = "邮箱不能为空")
    private String email;

    @ApiModelProperty(value = "密码")
    @NotNull(message = "密码不能为空")
    private String password;

    @ApiModelProperty(value = "盐")
    private String salt;

    /**
     * 状态( 0:禁用 1:启用)
     */
    @ApiModelProperty(value = "状态( 0:禁用 1:启用)")
    private Integer status;

    @TableField(exist = false)
    private List<RoleDto> roleDtos;

    @TableField(exist = false)
    private List<Long> roleIdList;

    @TableField(exist = false)
    private String token;

}

package com.langsin.oa.dto;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author wyy
 * @date 2019/10/16
 */
@Data
@TableName(value = "r_user_role")
public class UserRoleDto {

    /**
     * 角色id
     */
    @ApiModelProperty(value = "角色id")
    private Long roleId;

    /**
     * 用户id
     */
    @ApiModelProperty(value = "用户id")
    private Long userId;

}

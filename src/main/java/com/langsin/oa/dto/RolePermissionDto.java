package com.langsin.oa.dto;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author wyy
 * @date 2019/10/16
 */
@Data
@TableName(value = "r_role_permission")
public class RolePermissionDto {

    /**
     * 角色id
     */
    @ApiModelProperty(value = "角色id")
    private Long roleId;

    /**
     * 权限id
     */
    @ApiModelProperty(value = "权限id")
    private Long permissionId;
}

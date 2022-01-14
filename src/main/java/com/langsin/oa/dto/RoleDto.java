package com.langsin.oa.dto;

import com.baomidou.mybatisplus.annotation.TableName;
import com.langsin.oa.foundation.BaseDto;
import com.langsin.oa.foundation.BaseDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author wyy
 * @date 2019/10/11
 */
@Data
@TableName(value = "r_role")
public class RoleDto extends BaseDto {

    @ApiModelProperty(value = "角色名称")
    @NotNull(message = "角色名称不能为空")
    @Size(min = 1,max = 15,message = "角色名称为1-15个字符")
    private String name;

    @ApiModelProperty(value = "排列顺序")
    private Integer priority;

    @ApiModelProperty(value = "描述")
    private String description;

    @ApiModelProperty(value = "角色编码，用于分角色数据权限")
    private String code;
}

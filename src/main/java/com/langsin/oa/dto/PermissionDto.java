package com.langsin.oa.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.langsin.oa.foundation.BaseDto;
import com.langsin.oa.foundation.BaseDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author wyy
 * @date 2019/10/11
 */
@Data
@TableName(value = "r_permission")
public class PermissionDto extends BaseDto {

    @ApiModelProperty(value = "权限名称")
    private String name;

    @ApiModelProperty(value = "权限值")
    private String code;

    @ApiModelProperty(value = "vueURL路径")
    private String path;

    @ApiModelProperty(value = "组件")
    private String component;

    @ApiModelProperty(value = "图标")
    private String icon;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "菜单类型 （类型   0：目录   1：菜单   2：按钮）")
    private Integer type;

    @ApiModelProperty(value = "是否隐藏 0 是 1 否")
    private String hidden;

    @ApiModelProperty(value = "父类id")
    private Long parentId;

    @ApiModelProperty(value = "菜单名称")
    private String menuName;

    /**
     * 非数据库字段
     * 父菜单名称
     */
    @TableField(exist = false)
    private String parentName;

    /**
     * 非数据库字段
     * 菜单等级
     */
    @TableField(exist = false)
    private Integer level;

    /**
     * 非数据库字段
     * 子菜单
     */
    @TableField(exist = false)
    private List<PermissionDto> children;

}

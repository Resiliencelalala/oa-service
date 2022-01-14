package com.langsin.oa.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

/**
 * @Classname MenuRouterDto
 * @Description  拼接菜单组件
 * @author wyy
 * @since 2019-09-10
 * @Version 1.0
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class MenuRouterDto {

    private String name;
    private String path;
    private boolean hidden;
    private String redirect;
    private String component;
    private Boolean alwaysShow;
    private MenuRouterMetaDto meta;
    private List<MenuRouterDto> children;
}

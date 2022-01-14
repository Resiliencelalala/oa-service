package com.langsin.oa.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @Classname MenuMetaVo
 * @Description
 * @author wyy
 * @since 2019-09-10
 * @Version 1.0
 */
@Data
@AllArgsConstructor
public class MenuRouterMetaDto implements Serializable {

    private String title;
    private String icon;
}

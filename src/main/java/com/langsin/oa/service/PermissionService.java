package com.langsin.oa.service;

import com.langsin.oa.dto.PermissionDto;
import com.langsin.oa.foundation.Result;
import com.langsin.oa.foundation.Result;

import java.util.List;

/**
 *
 * @Description:  权限管理
 * @Author: wyy
 * @Date: 2019-10-17
 **/
public interface PermissionService {

    /**
     * 获取权限菜单树
     * @return
     */
    List<PermissionDto> selectMenuTree();

    /**
     * 添加菜单
     * @param permissionDto
     * @return
     */
    int insert(PermissionDto permissionDto);

    /**
     * 修改菜单
     * @param permissionDto
     * @return
     */
    int update(PermissionDto permissionDto);

    /**
     * 删除菜单
     * @param id 菜单id
     * @return
     */
    Result deleteById(Long id);
}

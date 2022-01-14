package com.langsin.oa.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.langsin.oa.dto.PermissionDto;
import com.langsin.oa.dto.RoleDto;
import com.langsin.oa.dto.RolePermissionDto;
import com.langsin.oa.foundation.PageModel;
import com.langsin.oa.foundation.PageModel;

import java.util.List;


/**
 *
 * @Description:  角色管理
 * @Author: wyy
 * @Date: 2019-10-17
 **/
public interface RoleService {

    /**
     * 获取所有角色
     * @return
     */
    List<RoleDto> selectAll();

    /**
     * 分页查询角色信息
     * @param pageModel 页数
     * @param name 名称
     * @return 数据
     */
    IPage<RoleDto> selectRoleAndPage(PageModel pageModel, String name);

    /**
     * 角色添加
     * @param roleDto
     * @return
     */
    int insert(RoleDto roleDto);

    /**
     * 角色编辑
     * @param roleDto
     * @return
     */
    int update(RoleDto roleDto);

    /**
     * 根据角色id获取菜单
     * @param roleId
     * @return
     */
    List<PermissionDto> selectMenuListByRoleId(Long roleId);

    /**
     * 分配权限
     * @param list
     * @return
     */
    int insertRolePerms(List<RolePermissionDto> list);

    /**
     * 删除
     *
     * @return
     */
    int delete(Long id);

}

package com.langsin.oa.service;

import com.langsin.oa.dto.MenuRouterDto;
import com.langsin.oa.dto.RoleDto;
import com.langsin.oa.dto.UserPasswordDto;
import com.langsin.oa.foundation.Result;
import com.langsin.oa.foundation.Result;

import java.util.List;
import java.util.Set;

/**
 *
 * @Description:  系统配置类
 * @Author: wyy
 * @Date: 2019-10-16
 **/
public interface AdminService {

    /**
     * 获取个人信息
     * @return 数据
     */
    List<RoleDto> selectUserInfo();

    /**
     * 根据用户id查找菜单树
     * @return
     */
    List<MenuRouterDto> selectMenuTree();

    /**
     * 获取权限标识,精确到按钮
     * @return
     */
    Set<String> selectPermsCodeByUserId();

    /**
     * shiro获取权限菜单
     * @return
     */
    Set<String> selectPermsCodeByUserIdAndShiro(Long userId);

    /**
     * shiro安全框架获取角色
     * @param userId 用户id
     * @return
     */
    Set<String> selectRoleNameByUserIdAndShiro(Long userId);

      /**
     * 修改密码
     * @param userPasswordDto 密码
     * @return
     */
      Result updateUserPassword(UserPasswordDto userPasswordDto);
}

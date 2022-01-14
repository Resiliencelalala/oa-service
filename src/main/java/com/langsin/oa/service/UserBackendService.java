package com.langsin.oa.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.langsin.oa.dto.UserBackendDto;
import com.langsin.oa.dto.UserBackendSingleDto;
import com.langsin.oa.dto.UserBackendStatusDto;
import com.langsin.oa.foundation.PageModel;
import com.langsin.oa.foundation.Result;
import com.langsin.oa.foundation.PageModel;
import com.langsin.oa.foundation.Result;

import java.util.List;

/**
 *
 * @Description:  系统用户管理
 * @Author: wyy
 * @Date: 2019-10-17
 **/
public interface UserBackendService {

    /**
     * 分页查询用户信息
     * @param pageModel 页数
     * @param realName 真实姓名
     * @return 数据
     */
    IPage<UserBackendDto> selectUserAndPage(PageModel pageModel, String realName);

    /**
     * 查询具备某个权限的所有用户
     * @param permissionCode
     * @return
     */
    List<UserBackendSingleDto> selectUserByPermission(String permissionCode,String roleCode);

    /**
     * 添加用户
     * @param userBackendDto
     * @return
     */
    Result insert(UserBackendDto userBackendDto);

    /**
     * 修改用户
     * @param userBackendDto
     * @return
     */
    Result update(UserBackendDto userBackendDto);

    /**
     * 删除
     *
     * @return
     */
    int delete(Long id);

    /**
     * 修改用户状态
     * @param userBackendStatusDto
     * @return
     */
    int updateUserByStatus(UserBackendStatusDto userBackendStatusDto);

}

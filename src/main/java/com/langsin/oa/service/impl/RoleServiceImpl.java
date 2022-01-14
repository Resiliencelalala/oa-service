package com.langsin.oa.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.langsin.oa.dto.PermissionDto;
import com.langsin.oa.dto.RoleDto;
import com.langsin.oa.dto.RolePermissionDto;
import com.langsin.oa.dto.UserRoleDto;
import com.langsin.oa.foundation.BaseException;
import com.langsin.oa.foundation.PageModel;
import com.langsin.oa.mapper.PermissionMapper;
import com.langsin.oa.mapper.RoleMapper;
import com.langsin.oa.mapper.RolePermissionMapper;
import com.langsin.oa.mapper.UserRoleMapper;
import com.langsin.oa.service.RoleService;
import com.langsin.oa.foundation.BaseException;
import com.langsin.oa.foundation.PageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @Description:  角色管理
 * @Author: wyy
 * @Date: 2019-10-17
 **/
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private PermissionMapper permissionMapper;
    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    public List<RoleDto> selectAll() {
        return roleMapper.selectList(new QueryWrapper<>());
    }

    @Override
    public IPage<RoleDto> selectRoleAndPage(PageModel pageModel, String name) {
        Page<RoleDto> page = new Page<>(pageModel.getPageNum(), pageModel.getPageSize());
        return roleMapper.selectPage(page, new QueryWrapper<RoleDto>().like("name","%"+name+"%"));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insert(RoleDto roleDto) {
        roleDto.setCreateTime(LocalDateTime.now());
        return roleMapper.insert(roleDto);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int update(RoleDto roleDto) {
        roleDto.setUpdateTime(LocalDateTime.now());
        return roleMapper.updateById(roleDto);
    }

    @Override
    public List<PermissionDto> selectMenuListByRoleId(Long roleId) {
        return permissionMapper.selectPermissionListByRoleId(roleId);
    }

    @Override
    public int insertRolePerms(List<RolePermissionDto> list) {
        rolePermissionMapper.delByRoleId(list.get(0).getRoleId());
        return rolePermissionMapper.insertList(list);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int delete(Long id) {
        QueryWrapper<UserRoleDto> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(UserRoleDto::getRoleId,id);
        Integer num = userRoleMapper.selectCount(queryWrapper);
        if(!num.equals(0)){
            throw new BaseException("此角色已赋予用户，不可删除");
        }
        int count = roleMapper.deleteById(id);
        rolePermissionMapper.delByRoleId(id);
        return count;
    }
}

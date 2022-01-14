package com.langsin.oa.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.langsin.oa.dto.PermissionDto;
import com.langsin.oa.enums.MenuConstant;
import com.langsin.oa.foundation.BaseException;
import com.langsin.oa.foundation.Result;
import com.langsin.oa.mapper.PermissionMapper;
import com.langsin.oa.service.PermissionService;
import com.langsin.oa.utils.VideoUtil;
import com.langsin.oa.foundation.BaseException;
import com.langsin.oa.foundation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 *
 * @Description:  权限管理
 * @Author: wyy
 * @Date: 2019-10-17
 **/
@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public List<PermissionDto> selectMenuTree() {
        List<PermissionDto> permissionDtos = new ArrayList<>();
        List<PermissionDto> menus = permissionMapper.selectList(new QueryWrapper<>());
        if (Objects.isNull(menus)) {
            return null;
        }
        menus.forEach(menu -> {
            if (menu.getParentId() == null || menu.getParentId() == 0) {
                menu.setLevel(0);
                if (VideoUtil.exists(permissionDtos, menu)) {
                    permissionDtos.add(menu);
                }
            }
        });
        permissionDtos.sort((o1, o2) -> o1.getSort().compareTo(o2.getSort()));
        VideoUtil.findChildren(permissionDtos, menus, 0);
        return permissionDtos;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insert(PermissionDto permissionDto) {
        verifyForm(permissionDto);
        permissionDto.setCreateTime(LocalDateTime.now());
        return permissionMapper.insert(permissionDto);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int update(PermissionDto permissionDto) {
        verifyForm(permissionDto);
        permissionDto.setUpdateTime(LocalDateTime.now());
        return permissionMapper.updateById(permissionDto);
    }

    @Override
    public Result deleteById(Long id) {
        List<Long> idList = permissionMapper.selectList(Wrappers.<PermissionDto>query().lambda().eq(PermissionDto::getParentId, id)).stream().map(PermissionDto::getId).collect(Collectors.toList());
        if (idList.size() > 0) {
            return Result.failed("菜单含有下级不能删除");
        }
        int count = permissionMapper.deleteById(id);

        return count > 0 ? Result.success() : Result.failed("删除异常,请稍后重试");
    }

    /**
     * 验证菜单参数是否正确
     */
    private void verifyForm(PermissionDto permissionDto) {
        //上级菜单类型
        int parentType = MenuConstant.MenuType.CATALOG.getValue();
        if (permissionDto.getParentId() != 0) {
            PermissionDto parentMenu = permissionMapper.selectOne(new QueryWrapper<PermissionDto>().eq("id",permissionDto.getParentId()));
            parentType = parentMenu.getType();
        }
        //目录、菜单
        if (permissionDto.getType() == MenuConstant.MenuType.CATALOG.getValue() ||
                permissionDto.getType() == MenuConstant.MenuType.MENU.getValue()) {
            if (parentType != MenuConstant.MenuType.CATALOG.getValue()) {
                throw new BaseException("上级菜单只能为目录类型");
            }
            return;
        }
        //按钮
        if (permissionDto.getType() == MenuConstant.MenuType.BUTTON.getValue()) {
            if (parentType != MenuConstant.MenuType.MENU.getValue()) {
                throw new BaseException("上级菜单只能为菜单类型");
            }
        }
    }
}

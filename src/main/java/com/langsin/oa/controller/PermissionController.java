package com.langsin.oa.controller;

import com.langsin.oa.dto.PermissionDto;
import com.langsin.oa.foundation.BaseException;
import com.langsin.oa.foundation.Result;
import com.langsin.oa.service.PermissionService;
import com.langsin.oa.foundation.BaseException;
import com.langsin.oa.foundation.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author wyy
 * @date 2019/10/17 9:07
 */
@Api(tags = {"权限管理"})
@RestController
@RequestMapping("/permission")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    /**
     * 获取菜单树
     *
     * @return
     */
    @GetMapping
    @ApiOperation("获取菜单树")
    public Result getMenuTree() {
        return Result.success(permissionService.selectMenuTree());
    }

    /**
     * 添加菜单
     *
     * @param permissionDto 数据
     * @return
     */
    @ApiOperation("添加菜单")
    @PostMapping
    @RequiresRoles(value={"admin"},logical= Logical.OR)
    public Result insert(@RequestBody PermissionDto permissionDto) {
        try {
            return permissionService.insert(permissionDto)>0 ? Result.success() : Result.failed();
        } catch (BaseException e) {
            return Result.failed(e.getMessage());
        }
    }

    /**
     * 修改菜单
     *
     * @param permissionDto 数据
     * @return
     */
    @ApiOperation("修改菜单")
    @PutMapping
    @RequiresRoles(value={"admin"},logical= Logical.OR)
    public Result update(@RequestBody PermissionDto permissionDto) {
        try {
            return permissionService.update(permissionDto)>0 ? Result.success() : Result.failed();
        } catch (BaseException e) {
            return Result.failed(e.getMessage());
        }
    }
    /**
     * 删除菜单
     *
     * @param id 菜单id
     * @return
     */
    @ApiOperation("删除菜单")
    @DeleteMapping
    @RequiresRoles(value={"admin"},logical= Logical.OR)
    public Result delete(@RequestParam("id") Long id) {
       return permissionService.deleteById(id);
    }

}

package com.langsin.oa.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.langsin.oa.dto.RoleDto;
import com.langsin.oa.dto.RolePermissionDto;
import com.langsin.oa.foundation.PageModel;
import com.langsin.oa.foundation.Result;
import com.langsin.oa.service.RoleService;
import com.langsin.oa.foundation.PageModel;
import com.langsin.oa.foundation.Result;
import com.langsin.oa.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author wyy
 * @date 2019/10/17 9:07
 */
@Api(tags = {"角色管理"})
@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping
    @ApiOperation("分页查询角色列表")
    public Result<IPage<RoleDto>> selectByNameAndPage(PageModel pageModel, @ApiParam(required = false,value = "角色名,可不填") @RequestParam("name")String name) {
        return  Result.success(roleService.selectRoleAndPage(pageModel,name));
    }

    /**
     * 根据角色id获取菜单
     *
     * @param roleId
     * @return
     */
    @ApiOperation("根据角色id获取菜单")
    @GetMapping("/selectRoleMenus/{roleId}")
    public Result findRoleMenus(@PathVariable("roleId") Long roleId) {
        return Result.success(roleService.selectMenuListByRoleId(roleId));
    }

    @GetMapping(value = "/selectAll")
    @ApiOperation("获取所有角色")
    public Result selectAll() {
        return Result.success(roleService.selectAll());
    }

    @ApiOperation("分配权限菜单")
    @PostMapping(value = "/insertRolePerms")
    @RequiresPermissions("sys:role:insert:perms")
    public Result insertRolePerms(@RequestBody List<RolePermissionDto> list) {
        return roleService.insertRolePerms(list) > 0 ? Result.success() : Result.failed();
    }

    @ApiOperation("新增角色数据")
    @PostMapping
    @RequiresPermissions("sys:role:add")
    public Result insert(@RequestBody RoleDto roleDto) {
        return roleService.insert(roleDto) > 0 ? Result.success() : Result.failed();
    }

    @ApiOperation("修改角色数据")
    @PutMapping
    @RequiresPermissions("sys:role:update")
    public Result update(@RequestBody RoleDto roleDto) {
        return roleService.update(roleDto) > 0 ? Result.success("修改成功") : Result.failed();
    }

    @ApiOperation("删除角色")
    @DeleteMapping
    @RequiresPermissions("sys:role:del")
    public Result delete(@RequestParam("id") Long id) {
        return roleService.delete(id) > 0 ? Result.success() : Result.failed();
    }
}

package com.langsin.oa.controller;

import com.langsin.oa.dto.UserPasswordDto;
import com.langsin.oa.foundation.Result;
import com.langsin.oa.service.AdminService;
import com.langsin.oa.foundation.Result;
import com.langsin.oa.service.AdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author wyy
 * @date 2019/10/16 9:07
 */
@Api(tags = {"系统配置"})
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @ApiOperation("获取用户角色信息")
    @GetMapping("/selectRole")
    public Result selectRole() {
        return Result.success(adminService.selectUserInfo());
    }

    /**
     * 获取路由
     *
     * @return
     */
    @ApiOperation("获取动态路由菜单")
    @GetMapping("/getRouters")
    public Result getRouters() {
        return Result.success(adminService.selectMenuTree());
    }

    @ApiOperation("获取用户操作权限")
    @GetMapping("/getPerms")
    public Result getPerms() {
        return Result.success(adminService.selectPermsCodeByUserId());
    }

    @ApiOperation("修改密码")
    @PutMapping("/updateBackendPassword")
    public Result updateBackendPassword(@RequestBody UserPasswordDto userPasswordDto) {
        return adminService.updateUserPassword(userPasswordDto);
    }
}

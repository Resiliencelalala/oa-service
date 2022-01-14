package com.langsin.oa.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.langsin.oa.dto.UserBackendDto;
import com.langsin.oa.dto.UserBackendStatusDto;
import com.langsin.oa.foundation.PageModel;
import com.langsin.oa.foundation.Result;
import com.langsin.oa.service.UserBackendService;
import com.langsin.oa.foundation.PageModel;
import com.langsin.oa.foundation.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author wyy
 * @date 2019/10/17 9:07
 */
@Api(tags = {"管理员用户"})
@RestController
@RequestMapping("/user")
public class UserBackendController {

    @Autowired
    private UserBackendService userBackendService;

    @GetMapping
    @ApiOperation("分页查询用户列表")
    public Result<IPage<UserBackendDto>> selectByNameAndPage(PageModel pageModel, @ApiParam(required = false,value = "用户账号,可不填") @RequestParam("realName")String realName) {
        return  Result.success(userBackendService.selectUserAndPage(pageModel,realName));
    }

    @ApiOperation("新增用户")
    @PostMapping
    @RequiresPermissions("sys:user:add")
    public Result insert(@RequestBody @Valid UserBackendDto userBackendDto, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return Result.failed(bindingResult.getFieldError().getDefaultMessage());
        }
        return userBackendService.insert(userBackendDto);
    }

    @ApiOperation("修改用户")
    @PutMapping
    @RequiresPermissions("sys:user:update")
    public Result update(@RequestBody UserBackendDto userBackendDto) {
        return userBackendService.update(userBackendDto);
    }

    @ApiOperation("删除用户")
    @DeleteMapping
    @RequiresPermissions("sys:user:del")
    public Result delete(@RequestParam("id") Long id) {
        return userBackendService.delete(id) > 0 ? Result.success() : Result.failed();
    }

    @ApiOperation("修改用户状态")
    @PutMapping("/updateUserByStatus")
    @RequiresPermissions("sys:user:update:status")
    public Result updateUserByStatus(@RequestBody UserBackendStatusDto userBackendStatusDto) {
        return userBackendService.updateUserByStatus(userBackendStatusDto) > 0 ? Result.success() : Result.failed();
    }
}

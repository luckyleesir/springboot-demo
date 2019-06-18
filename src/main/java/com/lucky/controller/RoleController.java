package com.lucky.controller;

import com.github.pagehelper.PageInfo;
import com.lucky.common.response.Result;
import com.lucky.model.SysPermission;
import com.lucky.model.SysRole;
import com.lucky.service.RoleService;
import com.lucky.util.PageUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 角色管理
 *
 * @author lucky
 */
@Api(tags = "RoleController", description = "角色管理")
@Slf4j
@RestController
@RequestMapping("/role")
public class RoleController {

    @Resource
    private RoleService roleService;

    @ApiOperation(value = "新增角色")
    @PostMapping("/add")
    public Result add(@RequestBody SysRole sysRole) {
        int count = roleService.add(sysRole);
        if (count == 0) {
            return Result.failed();
        }
        return Result.success(count);
    }

    @ApiOperation(value = "删除角色")
    @PostMapping("/delete")
    public Result delete(@RequestBody List<Long> roleIds) {
        int count = roleService.delete(roleIds);
        if (count < roleIds.size()) {
            return Result.failed();
        }
        return Result.success(count);
    }

    @ApiOperation(value = "角色详情")
    @GetMapping("/{roleId}")
    public Result detail(@PathVariable Long roleId) {
        SysRole sysRole = roleService.detail(roleId);
        return Result.success(sysRole);
    }

    @ApiOperation(value = "修改角色")
    @PostMapping("/update/{roleId}")
    public Result update(@PathVariable Long roleId, @RequestBody SysRole sysRole) {
        int count = roleService.update(roleId, sysRole);
        if (count == 0) {
            return Result.failed();
        }
        return Result.success(count);
    }

    @ApiOperation(value = "角色列表")
    @GetMapping("/list")
    public Result list(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "50") int pageSize, @RequestParam(required = false) String orderBy) {
        List<SysRole> sysRoleList = roleService.list(PageUtil.set(pageNum, pageSize, orderBy));
        return Result.success(new PageInfo<>(sysRoleList));
    }

    @ApiOperation(value = "获取角色所有权限")
    @GetMapping("/permission/{roleId}")
    public Result getPermissionList(@PathVariable Long roleId) {
        List<SysPermission> sysPermissionList = roleService.getPermissionList(roleId);
        return Result.success(sysPermissionList);
    }

    @ApiOperation(value = "修改角色权限")
    @PostMapping("/permission/update/{roleId}")
    public Result updateRolePermission(@PathVariable Long roleId, @RequestBody List<Long> permissionIds) {
        int count = roleService.updateRolePermission(roleId, permissionIds);
        if (count > 0) {
            return Result.success(count);
        }
        return Result.failed();
    }
}

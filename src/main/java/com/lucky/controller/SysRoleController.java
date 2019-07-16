package com.lucky.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lucky.common.response.Result;
import com.lucky.model.SysPermission;
import com.lucky.model.SysRole;
import com.lucky.service.ISysRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 角色管理
 *
 * @author lucky
 */
@Api(tags = "角色管理")
@Slf4j
@RestController
@RequestMapping("/role")
public class SysRoleController {

    @Resource
    private ISysRoleService sysRoleService;

    @ApiOperation(value = "新增角色")
    @PreAuthorize("hasAuthority('sys:role:add')")
    @PostMapping("/add")
    public Result add(@RequestBody SysRole sysRole) {
        boolean success = sysRoleService.add(sysRole);
        return Result.handle(success);
    }

    @ApiOperation(value = "删除角色")
    @PreAuthorize("hasAuthority('sys:role:delete')")
    @PostMapping("/delete")
    public Result delete(@RequestBody List<Long> roleIds) {
        boolean success = sysRoleService.delete(roleIds);
        return Result.handle(success);
    }

    @ApiOperation(value = "角色详情")
    @GetMapping("/{roleId}")
    public Result detail(@PathVariable Long roleId) {
        SysRole sysRole = sysRoleService.detail(roleId);
        return Result.success(sysRole);
    }

    @ApiOperation(value = "修改角色")
    @PreAuthorize("hasAuthority('sys:role:edit')")
    @PostMapping("/update/{roleId}")
    public Result update(@PathVariable Long roleId, @RequestBody SysRole sysRole) {
        boolean success = sysRoleService.update(roleId, sysRole);
        return Result.handle(success);
    }

    @ApiOperation(value = "角色列表")
    @PreAuthorize("hasAuthority('sys:role:read')")
    @GetMapping("/list")
    public Result list(@RequestParam(required = false) String name, @RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "50") int pageSize) {
        return Result.success(sysRoleService.list(new Page(pageNum, pageSize), name));
    }

    @ApiOperation(value = "所有角色列表")
    @PreAuthorize("hasAuthority('sys:role:read')")
    @GetMapping("/listAll")
    public Result listAll() {
        return Result.success(sysRoleService.list());
    }

    @ApiOperation(value = "获取角色所有权限")
    @GetMapping("/permission/{roleId}")
    public Result getPermissionList(@PathVariable Long roleId) {
        List<SysPermission> sysPermissionList = sysRoleService.getPermissionList(roleId);
        return Result.success(sysPermissionList);
    }

    @ApiOperation(value = "修改角色权限")
    @PostMapping("/permission/update/{roleId}")
    public Result updateRolePermission(@PathVariable Long roleId, @RequestBody List<Long> permissionIds) {
        boolean success = sysRoleService.updateRolePermission(roleId, permissionIds);
        return Result.handle(success);
    }

    @ApiOperation(value = "获取角色权限树")
    @GetMapping("/permissionTree/{roleId}")
    public Result getRolePermissionTree(@PathVariable Long roleId) {
        return Result.success(sysRoleService.getRolePermissionTree(roleId));
    }

}

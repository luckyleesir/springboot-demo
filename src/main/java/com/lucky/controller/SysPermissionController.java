package com.lucky.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lucky.common.response.Result;
import com.lucky.dto.PermissionNodeDto;
import com.lucky.model.SysPermission;
import com.lucky.service.ISysPermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 权限管理
 *
 * @author lucky
 */
@Api(tags = "权限管理")
@Slf4j
@RestController
@RequestMapping("/permission")
public class SysPermissionController {

    @Resource
    private ISysPermissionService sysPermissionService;

    @ApiOperation(value = "新增权限")
    @PreAuthorize("hasAuthority('sys:permission:add')")
    @PostMapping("/add")
    public Result add(@RequestBody SysPermission sysPermission) {
        boolean success = sysPermissionService.add(sysPermission);
        return Result.handle(success);
    }

    @ApiOperation(value = "删除权限")
    @PreAuthorize("hasAuthority('sys:permission:delete')")
    @PostMapping("/delete")
    public Result delete(@RequestBody List<Long> permissionIds) {
        boolean success = sysPermissionService.delete(permissionIds);
        return Result.handle(success);
    }

    @ApiOperation(value = "权限详情")
    @GetMapping("/{permissionId}")
    public Result detail(@PathVariable Long permissionId) {
        SysPermission sysPermission = sysPermissionService.detail(permissionId);
        return Result.success(sysPermission);
    }

    @ApiOperation(value = "修改权限")
    @PreAuthorize("hasAuthority('sys:permission:edit')")
    @PostMapping("/update/{permissionId}")
    public Result update(@PathVariable Long permissionId, @RequestBody SysPermission sysPermission) {
        boolean success = sysPermissionService.update(permissionId, sysPermission);
        return Result.handle(success);
    }

    @ApiOperation(value = "权限列表")
    @PreAuthorize("hasAuthority('sys:permission:read')")
    @GetMapping("/list")
    public Result list(@RequestParam(required = false) String name, @RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "50") int pageSize) {
        return Result.success(sysPermissionService.list(new Page<>(pageNum, pageSize), name));
    }

    @ApiOperation(value = "权限树层级")
    @GetMapping("/treeList")
    public Result treeList(@RequestParam(required = false) SysPermission sysPermission) {
        List<PermissionNodeDto> permissionNodeDtoList = sysPermissionService.treeList(sysPermission);
        return Result.success(permissionNodeDtoList);
    }

}

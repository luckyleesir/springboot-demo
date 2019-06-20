package com.lucky.controller;

import com.github.pagehelper.PageInfo;
import com.lucky.common.response.Result;
import com.lucky.dto.PermissionNodeDto;
import com.lucky.model.SysPermission;
import com.lucky.service.SysPermissionService;
import com.lucky.util.PageUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
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
    private SysPermissionService sysPermissionService;

    @ApiOperation(value = "新增权限")
    @PostMapping("/add")
    public Result add(@RequestBody SysPermission sysPermission) {
        int count = sysPermissionService.add(sysPermission);
        if (count == 0) {
            return Result.failed();
        }
        return Result.success(count);
    }

    @ApiOperation(value = "删除权限")
    @PostMapping("/delete")
    public Result delete(@RequestBody List<Long> permissionIds) {
        int count = sysPermissionService.delete(permissionIds);
        if (count < permissionIds.size()) {
            return Result.failed();
        }
        return Result.success(count);
    }

    @ApiOperation(value = "权限详情")
    @GetMapping("/{permissionId}")
    public Result detail(@PathVariable Long permissionId) {
        SysPermission sysPermission = sysPermissionService.detail(permissionId);
        return Result.success(sysPermission);
    }

    @ApiOperation(value = "修改权限")
    @PostMapping("/update/{permissionId}")
    public Result update(@PathVariable Long permissionId, @RequestBody SysPermission sysPermission) {
        int count = sysPermissionService.update(permissionId, sysPermission);
        if (count == 0) {
            return Result.failed();
        }
        return Result.success(count);
    }

    @ApiOperation(value = "权限列表")
    @GetMapping("/list")
    public Result list(@RequestParam(defaultValue = "1") int pageNum,
                       @RequestParam(defaultValue = "50") int pageSize,
                       @RequestParam(required = false) String orderBy) {
        List<SysPermission> sysPermissionList = sysPermissionService.list(PageUtil.set(pageNum, pageSize, orderBy));
        return Result.success(new PageInfo<>(sysPermissionList));
    }

    @ApiOperation(value = "权限树层级")
    @GetMapping("/treeList")
    public Result treeList() {
        List<PermissionNodeDto> permissionNodeDtoList = sysPermissionService.treeList();
        return Result.success(permissionNodeDtoList);
    }

}

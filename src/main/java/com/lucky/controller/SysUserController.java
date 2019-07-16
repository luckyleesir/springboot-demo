package com.lucky.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Maps;
import com.lucky.common.response.Result;
import com.lucky.dto.SysUserDto;
import com.lucky.model.SysUser;
import com.lucky.service.ISysUserService;
import com.lucky.util.JwtTokenUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 用户管理
 *
 * @author lucky
 */
@Api(tags = "用户管理")
@Slf4j
@RestController
@RequestMapping("/user")
public class SysUserController {

    @Resource
    private ISysUserService sysUserService;
    @Resource
    private JwtTokenUtil jwtTokenUtil;

    @ApiOperation(value = "用户注册")
    @PostMapping("/register")
    public Result register(@RequestBody SysUser sysUser) {
        boolean success = sysUserService.register(sysUser);
        return Result.handle(success);
    }

    @ApiOperation(value = "用户登录")
    @PostMapping("/login")
    public Result login(@RequestParam String username, @RequestParam String password) {
        String token = sysUserService.login(username, password);
        if (token == null) {
            return Result.failed("用户名或密码错误");
        }
        Map<String, String> tokenMap = Maps.newHashMap();
        tokenMap.put("token", token);
        tokenMap.put("tokenHead", jwtTokenUtil.getTokenHead());
        return Result.success(tokenMap);
    }

    @ApiOperation(value = "刷新token")
    @GetMapping("/token/refresh")
    public Result refreshToken(HttpServletRequest request) {
        String token = request.getHeader(jwtTokenUtil.getTokenHeader());
        String refreshToken = sysUserService.refreshToken(token);
        if (refreshToken == null) {
            return Result.failed();
        }
        Map<String, String> tokenMap = Maps.newHashMap();
        tokenMap.put("token", refreshToken);
        tokenMap.put("tokenHead", jwtTokenUtil.getTokenHead());
        return Result.success(tokenMap);
    }

    @ApiOperation(value = "用户登出")
    @PostMapping("/logout")
    public Result logout() {
        return Result.success();
    }

    @ApiOperation(value = "用户列表")
    @PreAuthorize("hasAuthority('sys:user:read')")
    @GetMapping("/list")
    public Result list(@RequestParam(required = false) String name, @RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "50") int pageSize) {
        return Result.success(sysUserService.list(new Page<>(pageNum, pageSize), name));
    }

    @ApiOperation(value = "用户列表(关联角色)")
    @PreAuthorize("hasAuthority('sys:user:read')")
    @GetMapping("/querylist")
    public Result listUser(SysUserDto sysUserDto, @RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "50") int pageSize) {
        return Result.success(sysUserService.list(new Page<>(pageNum, pageSize), sysUserDto));
    }

    @ApiOperation(value = "用户详情")
    @GetMapping("/{userId}")
    public Result detail(@PathVariable Long userId) {
        return Result.success(sysUserService.detail(userId));
    }


    @ApiOperation(value = "添加用户")
    @PreAuthorize("hasAuthority('sys:user:add')")
    @PostMapping("/add")
    public Result add(@RequestBody SysUser sysUser) {
        boolean success = sysUserService.add(sysUser);
        return Result.handle(success);
    }

    @ApiOperation(value = "修改用户")
    @PreAuthorize("hasAuthority('sys:user:edit')")
    @PostMapping("/update/{userId}")
    public Result update(@PathVariable Long userId, @RequestBody SysUser sysUser) {
        boolean success = sysUserService.update(userId, sysUser);
        return Result.handle(success);
    }

    @ApiOperation(value = "删除用户")
    @PreAuthorize("hasAuthority('sys:user:delete')")
    @PostMapping("/delete")
    public Result delete(@RequestBody List<Long> userIds) {
        int count = sysUserService.delete(userIds);
        if (count == userIds.size()) {
            return Result.success(count);
        }
        return Result.failed();
    }

    @ApiOperation(value = "修改用户角色")
    @PostMapping("/role/update/{userId}")
    public Result updateUserRole(@PathVariable Long userId, @RequestBody List<Long> roleIds) {
        boolean success = sysUserService.updateUserRole(userId, roleIds);
        return Result.handle(success);
    }

    @ApiOperation(value = "获取指定用户角色")
    @GetMapping("/role/{userId}")
    public Result getRoleList(@PathVariable Long userId) {
        return Result.success(sysUserService.getRoleList(userId));
    }

    @ApiOperation(value = "获取指定用户权限")
    @GetMapping("/permission/{userId}")
    public Result getPermissionList(@PathVariable Long userId) {
        return Result.success(sysUserService.getPermissionList(userId));
    }

    @ApiOperation(value = "获取指定用户菜单层级")
    @GetMapping("/menu/treeList")
    public Result menuTreeList() {
        return Result.success(sysUserService.getUserMenu());
    }

}

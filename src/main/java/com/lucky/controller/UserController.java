package com.lucky.controller;

import com.github.pagehelper.PageInfo;
import com.lucky.common.response.Result;
import com.lucky.model.SysUser;
import com.lucky.service.UserService;
import com.lucky.util.PageUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用户管理
 *
 * @author lucky
 */
@Api(tags = "UserController",description = "用户管理")
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @ApiOperation(value = "用户注册")
    @PostMapping("/add")
    public Result register(@RequestBody SysUser sysUser) {
        int count = userService.register(sysUser);
        if (count==0){
            return Result.failed();
        }
        return Result.success(count);
    }

    @ApiOperation(value = "用户登录")
    @PostMapping("/login")
    public Result login(@RequestParam String username, @RequestParam String password) {
        //添加用户认证信息
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username, password);
        //进行验证，这里可以捕获异常，然后返回对应信息
        String msg = "";
        try {
            subject.login(usernamePasswordToken);
        } catch (IncorrectCredentialsException ice) {
            msg = "用户名或密码不正确";
        } catch (LockedAccountException lae) {
            msg = "账户已锁定";
        } catch (ExcessiveAttemptsException eae) {
            msg = "用户名或密码错误次数过多";
        } catch (AuthenticationException ae) {
            //通过处理Shiro的运行时AuthenticationException就可以控制用户登录失败或密码错误时的情景
            msg = "用户名或密码不正确";
        }

        if (StringUtils.isBlank(msg)) {
            return Result.success();
        }
        return Result.failed(msg);
    }

    @ApiOperation(value = "用户登出")
    @PostMapping("/logout")
    public Result logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return Result.success();
    }

    @ApiOperation(value = "用户列表")
    @GetMapping("/list")
    public Result list(@RequestParam(required = false) String name,
                       @RequestParam(defaultValue = "1") int pageNum,
                       @RequestParam(defaultValue = "50") int pageSize,
                       @RequestParam(required = false) String orderBy) {
        List<SysUser> sysUserList = userService.list(name, PageUtil.set(pageNum, pageSize, orderBy));
        return Result.success(new PageInfo<>(sysUserList));
    }

    @ApiOperation(value = "用户详情")
    @GetMapping("/{userId}")
    public Result detail(@PathVariable Long userId) {
        return Result.success(userService.detail(userId));
    }

    @ApiOperation(value = "修改用户")
    @PostMapping("/update/{userId}")
    public Result update(@PathVariable Long userId, @RequestBody SysUser sysUser) {
        int count = userService.update(userId, sysUser);
        if (count == 0) {
            return Result.failed();
        }
        return Result.success(count);
    }

    @ApiOperation(value = "删除用户")
    @PostMapping("/delete/{userId}")
    public Result delete(@PathVariable Long userId) {
        int count = userService.delete(userId);
        if (count == 0) {
            return Result.failed();
        }
        return Result.success(count);
    }

    @ApiOperation(value = "修改用户角色")
    @PostMapping("/role/update/{userId}")
    public Result updateUserRole(@PathVariable Long userId, @RequestBody List<Long> roleIds) {
        int count = userService.updateUserRole(userId, roleIds);
        if (count == 0) {
            return Result.failed();
        }
        return Result.success(count);
    }

    @ApiOperation(value = "获取指定用户角色")
    @GetMapping("/role/{userId}")
    public Result getRoleList(@PathVariable Long userId) {
        return Result.success(userService.getRoleList(userId));
    }

    @ApiOperation(value = "获取指定用户权限")
    @GetMapping("/permission/{userId}")
    public Result getPermissionList(@PathVariable Long userId) {
        return Result.success(userService.getPermissionList(userId));
    }

}

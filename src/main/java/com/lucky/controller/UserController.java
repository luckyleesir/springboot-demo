package com.lucky.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.lucky.common.response.Result;
import com.lucky.dto.UserDto;
import com.lucky.model.SysUser;
import com.lucky.service.UserService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 账户管理
 *
 * @author lucky
 */
@Api(tags = "账户管理")
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @ApiOperation(value = "新增用户")
    @PostMapping("/add")
    public Result add(@RequestBody UserDto userDto) throws Exception {
        try {
            userService.add(userDto);
        } catch (Exception e) {
            throw e;
        }
        return Result.success(null);
    }

    @ApiOperation(value = "用户列表")
    @GetMapping("/list")
    public Result list(@RequestParam(defaultValue = "1") int pageNum,
                       @RequestParam(defaultValue = "50") int pageSize,
                       @RequestParam(defaultValue = "") String orderBy) {
        Page page = new Page();
        page.setPageNum(pageNum);
        page.setPageSize(pageSize);
        page.setOrderBy(orderBy);
        List<SysUser> sysUserList = userService.list(page);
        PageInfo<SysUser> sysUserPage = new PageInfo<>(sysUserList);
        return Result.success(sysUserPage);
    }

}

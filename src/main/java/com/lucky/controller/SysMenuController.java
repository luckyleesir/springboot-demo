package com.lucky.controller;

import com.lucky.common.response.Result;
import com.lucky.dto.MenuNodeDto;
import com.lucky.service.SysMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 菜单管理
 *
 * @author lucky
 */
@Api(tags = "菜单管理")
@Slf4j
@RestController
@RequestMapping("/menu")
public class SysMenuController {

    @Resource
    private SysMenuService sysMenuService;

    @ApiOperation(value = "菜单树层级")
    @GetMapping("/treeList")
    public Result treeList() {
        List<MenuNodeDto> menuNodeDtoList = sysMenuService.treeList();
        return Result.success(menuNodeDtoList);
    }

}

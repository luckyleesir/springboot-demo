package com.lucky.service;

import com.lucky.dto.MenuNodeDto;

import java.util.List;

/**
 * 用户权限
 *
 * @author: lucky
 * @date: 2019/6/12 16:02
 */
public interface SysMenuService {

    /**
     * 获取菜单树树结构
     *
     * @return MenuNodeDto
     */
    List<MenuNodeDto> treeList();

}

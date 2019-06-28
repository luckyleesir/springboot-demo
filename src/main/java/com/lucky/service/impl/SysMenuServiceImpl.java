package com.lucky.service.impl;

import com.lucky.dto.MenuNodeDto;
import com.lucky.mapper.auto.SysMenuMapper;
import com.lucky.model.SysMenu;
import com.lucky.model.SysMenuExample;
import com.lucky.service.SysMenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: lucky
 * @date: 2019/6/12 16:03
 */
@Slf4j
@Service
public class SysMenuServiceImpl implements SysMenuService {

    @Resource
    private SysMenuMapper sysMenuMapper;

    @Override
    public List<MenuNodeDto> treeList() {
        List<SysMenu> sysMenuList = sysMenuMapper.selectByExample(new SysMenuExample());
        return sysMenuList.stream().filter(sysMenu -> sysMenu.getPid().equals(0L)).map(sysMenu -> convert(sysMenu, sysMenuList)).collect(Collectors.toList());
    }

    /**
     * 将菜单转换为带有子级的权限对象
     * 当找不到子级权限的时候map操作不会再递归调用covert
     */
    private MenuNodeDto convert(SysMenu sysMenu, List<SysMenu> sysMenuList) {
        MenuNodeDto menuNodeDto = new MenuNodeDto();
        BeanUtils.copyProperties(sysMenu, menuNodeDto);
        List<MenuNodeDto> children = sysMenuList.stream().filter(subMenu -> subMenu.getPid().equals(sysMenu.getMenuId())).map(subMenu -> convert(subMenu, sysMenuList)).collect(Collectors.toList());
        menuNodeDto.setChildren(children);
        return menuNodeDto;
    }
}

package com.lucky.dto;

import com.lucky.model.SysMenu;
import com.lucky.model.SysPermission;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author: lucky
 * @date: 2019/6/17 19:55
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class MenuNodeDto extends SysMenu {

    /**
     * 子菜单
     */
    private List<MenuNodeDto> children;
}

package com.lucky.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author: lucky
 * @date: 2019/6/17 19:55
 */
@Data
public class MenuNodeDto {

    /**
     * 菜单id
     */
    @ApiModelProperty(value = "菜单id")
    private Long menuId;

    /**
     * 父级菜单id
     */
    @ApiModelProperty(value = "父级菜单id")
    private Long pid;

    /**
     * 菜单名称
     */
    @ApiModelProperty(value = "菜单名称")
    private String title;

    /**
     * 菜单图标
     */
    @ApiModelProperty(value = "菜单图标")
    private String icon;

    /**
     * 菜单url
     */
    @ApiModelProperty(value = "菜单url")
    private String href;

    /**
     * 子菜单
     */
    private List<MenuNodeDto> children;
}

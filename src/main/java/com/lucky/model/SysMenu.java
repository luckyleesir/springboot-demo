package com.lucky.model;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value="com.lucky.model.SysMenu")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SysMenu implements Serializable {
    /**
     * 菜单id
     */
    @ApiModelProperty(value="菜单id")
    private Long menuId;

    /**
     * 父级菜单id
     */
    @ApiModelProperty(value="父级菜单id")
    private Long pid;

    /**
     * 菜单名称
     */
    @ApiModelProperty(value="菜单名称")
    private String title;

    /**
     * 菜单图标
     */
    @ApiModelProperty(value="菜单图标")
    private String icon;

    /**
     * 菜单url
     */
    @ApiModelProperty(value="菜单url")
    private String href;

    /**
     * 排序
     */
    @ApiModelProperty(value="排序")
    private Integer sort;

    private static final long serialVersionUID = 1L;

    public String toJson() {
        return JSON.toJSONString(this);
    }
}
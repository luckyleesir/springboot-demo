package com.lucky.model;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value="com.lucky.model.SysPermission")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SysPermission implements Serializable {
    /**
     * 权限id
     */
    @ApiModelProperty(value="权限id")
    private Long permissionId;

    /**
     * 父级权限id
     */
    @ApiModelProperty(value="父级权限id")
    private Long pid;

    /**
     * 权限名称
     */
    @ApiModelProperty(value="权限名称")
    private String name;

    /**
     * 权限值
     */
    @ApiModelProperty(value="权限值")
    private String value;

    /**
     * 权限描述
     */
    @ApiModelProperty(value="权限描述")
    private String description;

    /**
     * 权限类型：0->目录；1->菜单；2->按钮（接口绑定权限）
     */
    @ApiModelProperty(value="权限类型：0->目录；1->菜单；2->按钮（接口绑定权限）")
    private Boolean type;

    /**
     * 启用状态；0->禁用；1->启用
     */
    @ApiModelProperty(value="启用状态；0->禁用；1->启用")
    private Boolean status;

    /**
     * 创建时间
     */
    @ApiModelProperty(value="创建时间")
    private Date createTime;

    /**
     * 最后修改时间
     */
    @ApiModelProperty(value="最后修改时间")
    private Date updateTime;

    private static final long serialVersionUID = 1L;

    public String toJson() {
        return JSON.toJSONString(this);
    }
}
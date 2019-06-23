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

@ApiModel(value="com.lucky.model.SysRole")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SysRole implements Serializable {
    /**
     * 角色id
     */
    @ApiModelProperty(value="角色id")
    private Long roleId;

    /**
     * 角色名称
     */
    @ApiModelProperty(value="角色名称")
    private String name;

    /**
     * 角色描述
     */
    @ApiModelProperty(value="角色描述")
    private String description;

    /**
     * 启用状态；0->禁用；1->启用
     */
    @ApiModelProperty(value="启用状态；0->禁用；1->启用")
    private Byte status;

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
package com.lucky.model;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value="com.lucky.model.SysRolePermission")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SysRolePermission implements Serializable {
    /**
     * 角色权限id
     */
    @ApiModelProperty(value="角色权限id")
    private Long id;

    /**
     * 角色id
     */
    @ApiModelProperty(value="角色id")
    private Long roleId;

    /**
     * 权限id
     */
    @ApiModelProperty(value="权限id")
    private Long permissionId;

    private static final long serialVersionUID = 1L;

    public String toJson() {
        return JSON.toJSONString(this);
    }
}
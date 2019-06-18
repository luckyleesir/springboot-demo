package com.lucky.model;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value="com.lucky.model.SysUserRole")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SysUserRole implements Serializable {
    /**
     * 用户角色id
     */
    @ApiModelProperty(value="用户角色id")
    private Long id;

    /**
     * 用户id
     */
    @ApiModelProperty(value="用户id")
    private Long userId;

    /**
     * 角色id
     */
    @ApiModelProperty(value="角色id")
    private Long roleId;

    private static final long serialVersionUID = 1L;

    public String toJson() {
        return JSON.toJSONString(this);
    }
}
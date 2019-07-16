package com.lucky.dto;

import com.lucky.model.SysUser;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户列表对象
 *
 * @author: lucky
 * @date: 2019/7/15 20:01
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "SysUserDto对象", description = "用户列表对象")
public class SysUserDto extends SysUser {

    @ApiModelProperty(value = "角色id(逗号分隔)")
    private String roleId;

    @ApiModelProperty(value = "角色名(逗号分隔)")
    private String roleName;

}

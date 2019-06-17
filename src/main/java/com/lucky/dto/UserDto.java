package com.lucky.dto;

import com.lucky.model.SysPermission;
import com.lucky.model.SysRole;
import com.lucky.model.SysUser;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author: lucky
 * @date: 2019/6/12 16:06
 */
@ApiModel(value = "用户参数")
@EqualsAndHashCode(callSuper = true)
@Data
public class UserDto extends SysUser {

    @ApiModelProperty(value = "用户角色")
    private List<SysRole> sysRoleList;

    @ApiModelProperty(value = "用户权限")
    private List<SysPermission> sysPermissionList;
}

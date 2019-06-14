package com.lucky.dto;

import com.lucky.model.SysPermission;
import com.lucky.model.SysRole;
import lombok.*;

import java.util.List;

/**
 * @author: lucky
 * @date: 2019/6/12 16:12
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class RoleDto extends SysRole {

    private List<SysPermission> sysPermissionList;
}

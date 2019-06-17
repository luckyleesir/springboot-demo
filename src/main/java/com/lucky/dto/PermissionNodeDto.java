package com.lucky.dto;

import com.lucky.model.SysPermission;
import lombok.*;

import java.util.List;

/**
 * @author: lucky
 * @date: 2019/6/17 19:55
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PermissionNodeDto extends SysPermission {

    /**
     * 子权限
     */
    private List<PermissionNodeDto> children;
}

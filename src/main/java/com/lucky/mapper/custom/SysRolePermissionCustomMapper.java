package com.lucky.mapper.custom;

import com.lucky.model.SysPermission;
import com.lucky.model.SysRolePermission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: lucky
 * @date: 2019/6/17 20:31
 */
public interface SysRolePermissionCustomMapper {

    /**
     * 批量插入角色和权限关系
     *
     * @param list 角色权限关系
     * @return 插入数量
     */
    int insertList(@Param("list") List<SysRolePermission> list);

    /***
     * 根据角色获取权限
     * @param roleId 角色id
     * @return 角色权限
     */
    List<SysPermission> getPermissionList(@Param("roleId") Long roleId);
}

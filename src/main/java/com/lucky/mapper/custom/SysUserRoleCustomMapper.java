package com.lucky.mapper.custom;

import com.lucky.model.SysPermission;
import com.lucky.model.SysRole;
import com.lucky.model.SysUserRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户角色自定义mapper
 *
 * @author: lucky
 * @date: 2019/6/18 10:14
 */
public interface SysUserRoleCustomMapper {

    /**
     * 批量插入用户角色
     *
     * @param sysUserRoleList 用户角色关系
     * @return 插入的条数
     */
    int insertList(@Param("list") List<SysUserRole> sysUserRoleList);

    /**
     * 获取指定用户角色
     *
     * @param userId 用户id
     * @return 角色列表
     */
    List<SysRole> getRoleList(@Param("userId") Long userId);

    /**
     * 获取指定用户权限
     *
     * @param userId 用户id
     * @return 权限列表
     */
    List<SysPermission> getPermissionList(@Param("userId") Long userId);
}

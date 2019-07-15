package com.lucky.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lucky.model.SysPermission;
import com.lucky.model.SysRole;
import com.lucky.model.SysUserRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 用户角色表 Mapper 接口
 * </p>
 *
 * @author lucky
 * @since 2019-07-15
 */
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {
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

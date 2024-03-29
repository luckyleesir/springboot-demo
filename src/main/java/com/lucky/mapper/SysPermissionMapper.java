package com.lucky.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lucky.model.SysPermission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 权限表 Mapper 接口
 * </p>
 *
 * @author lucky
 * @since 2019-07-15
 */
public interface SysPermissionMapper extends BaseMapper<SysPermission> {
    /**
     * 根据角色获取权限
     *
     * @param roleId 角色id
     * @return 角色权限
     */
    List<SysPermission> getRolePermissionList(@Param("roleId") Long roleId);

    /**
     * 获取指定用户权限
     *
     * @param userId 用户id
     * @return 权限列表
     */
    List<SysPermission> getUserPermissionList(@Param("userId") Long userId);
}

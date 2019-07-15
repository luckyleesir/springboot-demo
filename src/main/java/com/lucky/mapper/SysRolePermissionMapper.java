package com.lucky.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lucky.model.SysPermission;
import com.lucky.model.SysRolePermission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 角色权限表 Mapper 接口
 * </p>
 *
 * @author lucky
 * @since 2019-07-15
 */
public interface SysRolePermissionMapper extends BaseMapper<SysRolePermission> {

    /***
     * 根据角色获取权限
     * @param roleId 角色id
     * @return 角色权限
     */
    List<SysPermission> getPermissionList(@Param("roleId") Long roleId);
}

package com.lucky.service;

import com.github.pagehelper.Page;
import com.lucky.model.SysPermission;
import com.lucky.model.SysRole;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 角色管理
 *
 * @author: lucky
 * @date: 2019/6/12 16:01
 */
public interface SysRoleService {

    /**
     * 添加角色
     *
     * @param sysRole 角色新增参数
     * @return 添加成功的条数
     */
    @Transactional(rollbackFor = {Error.class, Exception.class})
    int add(SysRole sysRole);

    /**
     * 批量删除角色
     *
     * @param roleIds 角色id数组
     * @return 删除成功的条数
     */
    @Transactional(rollbackFor = {Error.class, Exception.class})
    int delete(List<Long> roleIds);

    /**
     * 角色详情
     *
     * @param roleId 角色id
     * @return SysRole
     */
    SysRole detail(Long roleId);

    /**
     * 修改角色
     *
     * @param sysRole 角色修改参数
     * @param roleId  角色id
     * @return 修改成功条数
     */
    int update(Long roleId, SysRole sysRole);

    /**
     * 角色列表
     *
     * @param page 分页信息
     * @return 角色list
     */
    List<SysRole> list(Page page);

    /**
     * 获取指定角色权限
     *
     * @param roleId 角色id
     * @return 角色权限列表
     */
    List<SysPermission> getPermissionList(Long roleId);

    /**
     * 修改角色权限
     *
     * @param roleId        角色id
     * @param permissionIds 权限id数组
     * @return 修改的数量
     */
    @Transactional(rollbackFor = {Error.class, Exception.class})
    int updateRolePermission(Long roleId, List<Long> permissionIds);
}

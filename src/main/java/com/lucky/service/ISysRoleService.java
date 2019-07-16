package com.lucky.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lucky.dto.RolePermissionTreeDto;
import com.lucky.model.SysPermission;
import com.lucky.model.SysRole;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author lucky
 * @since 2019-07-15
 */
public interface ISysRoleService extends IService<SysRole> {

    /**
     * 添加角色
     *
     * @param sysRole 角色新增参数
     * @return 成功或失败
     */
    @Transactional(rollbackFor = {Error.class, Exception.class})
    boolean add(SysRole sysRole);

    /**
     * 批量删除角色
     *
     * @param roleIds 角色id数组
     * @return 成功或失败
     */
    @Transactional(rollbackFor = {Error.class, Exception.class})
    boolean delete(List<Long> roleIds);

    /**
     * 角色详情
     *
     * @param roleId 角色id
     * @return 角色详情
     */
    SysRole detail(Long roleId);

    /**
     * 修改角色
     *
     * @param roleId  角色id
     * @param sysRole 角色修改参数
     * @return 成功或失败
     */
    boolean update(Long roleId, SysRole sysRole);

    /**
     * 角色列表
     *
     * @param page 分页信息
     * @param name 查询关键字
     * @return 角色list
     */
    IPage<SysRole> list(Page<SysRole> page, String name);

    /**
     * 所有角色列表
     *
     * @return 角色
     */
    List<SysRole> listAll();

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
     * @return 成功或失败
     */
    @Transactional(rollbackFor = {Error.class, Exception.class})
    boolean updateRolePermission(Long roleId, List<Long> permissionIds);

    /**
     * 角色权限树
     *
     * @param roleId 角色id
     * @return 角色权限树结构
     */
    List<RolePermissionTreeDto> getRolePermissionTree(Long roleId);
}

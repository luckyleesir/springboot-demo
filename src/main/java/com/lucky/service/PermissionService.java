package com.lucky.service;

import com.github.pagehelper.Page;
import com.lucky.dto.PermissionNodeDto;
import com.lucky.model.SysPermission;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 用户权限
 *
 * @author: lucky
 * @date: 2019/6/12 16:02
 */
public interface PermissionService {

    /**
     * 添加权限
     *
     * @param sysPermission 权限新增参数
     * @return 添加成功的条数
     */
    @Transactional(rollbackFor = {Error.class, Exception.class})
    int add(SysPermission sysPermission);

    /**
     * 批量删除权限
     *
     * @param permissionIds 权限id数组
     * @return 删除成功的条数
     */
    @Transactional(rollbackFor = {Error.class, Exception.class})
    int delete(List<Long> permissionIds);

    /**
     * 权限详情
     *
     * @param permissionId 权限id
     * @return SysPermission
     */
    SysPermission detail(Long permissionId);

    /**
     * 修改权限
     *
     * @param sysPermission 权限修改参数
     * @return 修改成功条数
     */
    int update(Long permissionId, SysPermission sysPermission);

    /**
     * 权限列表
     *
     * @param page 分页信息
     * @return 权限list
     */
    List<SysPermission> list(Page page);

    /**
     * 获取权限树结构
     *
     * @return PermissionNodeDto
     */
    List<PermissionNodeDto> treeList();

}

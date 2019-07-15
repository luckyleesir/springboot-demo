package com.lucky.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lucky.dto.PermissionNodeDto;
import com.lucky.model.SysPermission;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 权限表 服务类
 * </p>
 *
 * @author lucky
 * @since 2019-07-15
 */
public interface ISysPermissionService extends IService<SysPermission> {

    /**
     * 添加权限
     *
     * @param sysPermission 权限新增参数
     * @return 成功或失败
     */
    @Transactional(rollbackFor = {Error.class, Exception.class})
    boolean add(SysPermission sysPermission);

    /**
     * 批量删除权限
     *
     * @param permissionIds 权限id数组
     * @return 成功或失败
     */
    @Transactional(rollbackFor = {Error.class, Exception.class})
    boolean delete(List<Long> permissionIds);

    /**
     * 权限详情
     *
     * @param permissionId 权限id
     * @return 权限详情
     */
    SysPermission detail(Long permissionId);

    /**
     * 修改权限
     *
     * @param permissionId  权限id
     * @param sysPermission 权限修改参数
     * @return 成功或失败
     */
    boolean update(Long permissionId, SysPermission sysPermission);

    /**
     * 权限列表
     *
     * @param page 分页信息
     * @param name 查询关键字
     * @return 权限list
     */
    IPage<SysPermission> list(Page<SysPermission> page, String name);

    /**
     * 获取权限树结构
     *
     * @param selectParam 过滤条件
     * @return 权限树结构
     */
    List<PermissionNodeDto> treeList(SysPermission selectParam);

}

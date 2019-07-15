package com.lucky.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lucky.dto.RolePermissionTreeDto;
import com.lucky.mapper.SysRoleMapper;
import com.lucky.mapper.SysRolePermissionMapper;
import com.lucky.model.SysPermission;
import com.lucky.model.SysRole;
import com.lucky.model.SysRolePermission;
import com.lucky.service.ISysRolePermissionService;
import com.lucky.service.ISysPermissionService;
import com.lucky.service.ISysRoleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author lucky
 * @since 2019-07-15
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {

    @Resource
    private SysRolePermissionMapper sysRolePermissionMapper;
    @Resource
    private ISysPermissionService sysPermissionService;
    @Resource
    private ISysRolePermissionService sysRolePermissionService;

    @Override
    public boolean add(SysRole sysRole) {
        return this.save(sysRole);
    }

    @Override
    public boolean delete(List<Long> roleIds) {
        return this.removeByIds(roleIds);
    }

    @Override
    public SysRole detail(Long roleId) {
        return this.getById(roleId);
    }

    @Override
    public boolean update(Long roleId, SysRole sysRole) {
        sysRole.setRoleId(roleId);
        return this.updateById(sysRole);
    }

    @Override
    public IPage<SysRole> list(Page<SysRole> page, String name) {
        LambdaQueryWrapper<SysRole> sysRoleLambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(name)) {
            sysRoleLambdaQueryWrapper.like(SysRole::getName, name).or().like(SysRole::getDescription, name);
        }
        return this.page(page, sysRoleLambdaQueryWrapper);
    }

    @Override
    public List<SysPermission> getPermissionList(Long roleId) {
        return sysRolePermissionMapper.getPermissionList(roleId);
    }

    @Override
    public boolean updateRolePermission(Long roleId, List<Long> permissionIds) {
        //先删除原有关系
        LambdaQueryWrapper<SysRolePermission> sysRolePermissionLambdaQueryWrapper = new LambdaQueryWrapper<>();
        sysRolePermissionLambdaQueryWrapper.eq(SysRolePermission::getRoleId, roleId);
        sysRolePermissionService.remove(sysRolePermissionLambdaQueryWrapper);

        List<SysRolePermission> sysRolePermissionList = new ArrayList<>();
        for (Long permissionId : permissionIds) {
            SysRolePermission sysRolePermission = new SysRolePermission();
            sysRolePermission.setRoleId(roleId);
            sysRolePermission.setPermissionId(permissionId);
            sysRolePermissionList.add(sysRolePermission);
        }
        return sysRolePermissionService.saveBatch(sysRolePermissionList);
    }

    @Override
    public List<RolePermissionTreeDto> getRolePermissionTree(Long roleId) {
        //找出该角色所有权限id
        List<SysPermission> rolePermissions = getPermissionList(roleId);
        List<Long> permissionIds = rolePermissions.stream().map(SysPermission::getPermissionId).collect(Collectors.toList());
        List<SysPermission> sysPermissionList = sysPermissionService.list();
        return sysPermissionList.stream().filter(sysPermission -> sysPermission.getPid().equals(0L)).map(sysPermission -> convert(sysPermission, sysPermissionList, permissionIds)).collect(Collectors.toList());
    }


    /**
     * 将权限转换为带有子级的权限对象
     * 当找不到子级权限的时候map操作不会再递归调用covert
     */
    private RolePermissionTreeDto convert(SysPermission sysPermission, List<SysPermission> sysPermissionList, List<Long> permissionIds) {
        RolePermissionTreeDto rolePermissionTreeDto = new RolePermissionTreeDto();
        rolePermissionTreeDto.setName(sysPermission.getName());
        rolePermissionTreeDto.setValue(sysPermission.getPermissionId().toString());
        if (permissionIds.contains(sysPermission.getPermissionId())) {
            rolePermissionTreeDto.setChecked(true);
        }
        List<RolePermissionTreeDto> children = sysPermissionList.stream().filter(subPermission -> subPermission.getPid().equals(sysPermission.getPermissionId())).map(subPermission -> convert(subPermission, sysPermissionList, permissionIds)).collect(Collectors.toList());
        rolePermissionTreeDto.setList(children);
        return rolePermissionTreeDto;
    }

}

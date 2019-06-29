package com.lucky.service.impl;

import com.github.pagehelper.Page;
import com.lucky.dto.RolePermissionTreeDto;
import com.lucky.mapper.auto.SysPermissionMapper;
import com.lucky.mapper.auto.SysRoleMapper;
import com.lucky.mapper.auto.SysRolePermissionMapper;
import com.lucky.mapper.custom.SysRolePermissionCustomMapper;
import com.lucky.model.*;
import com.lucky.service.SysPermissionService;
import com.lucky.service.SysRoleService;
import com.lucky.util.PageUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 角色管理
 *
 * @author: lucky
 * @date: 2019/6/12 16:02
 */
@Service
public class SysRoleServiceImpl implements SysRoleService {

    @Resource
    private SysRoleMapper sysRoleMapper;
    @Resource
    private SysRolePermissionCustomMapper sysRolePermissionCustomMapper;
    @Resource
    private SysRolePermissionMapper sysRolePermissionMapper;
    @Resource
    private SysPermissionService sysPermissionService;
    @Resource
    private SysPermissionMapper sysPermissionMapper;

    @Override
    public int add(SysRole sysRole) {
        return sysRoleMapper.insertSelective(sysRole);
    }

    @Override
    public int delete(List<Long> roleIds) {
        SysRoleExample sysRoleExample = new SysRoleExample();
        sysRoleExample.createCriteria().andRoleIdIn(roleIds);
        return sysRoleMapper.deleteByExample(sysRoleExample);
    }

    @Override
    public SysRole detail(Long roleId) {
        return sysRoleMapper.selectByPrimaryKey(roleId);
    }

    @Override
    public int update(Long roleId, SysRole sysRole) {
        sysRole.setRoleId(roleId);
        return sysRoleMapper.updateByPrimaryKeySelective(sysRole);
    }

    @Override
    public List<SysRole> list(String name, Page page) {
        PageUtil.start(page);
        SysRoleExample sysRoleExample = new SysRoleExample();
        if (StringUtils.isNotBlank(name)) {
            String like = "%" + name + "%";
            sysRoleExample.createCriteria().andNameLike(like);
            sysRoleExample.or().andDescriptionLike(like);
        }
        return sysRoleMapper.selectByExample(sysRoleExample);
    }

    @Override
    public List<SysPermission> getPermissionList(Long roleId) {
        return sysRolePermissionCustomMapper.getPermissionList(roleId);
    }

    @Override
    public int updateRolePermission(Long roleId, List<Long> permissionIds) {
        //先删除原有关系
        SysRolePermissionExample sysRolePermissionExample = new SysRolePermissionExample();
        sysRolePermissionExample.createCriteria().andRoleIdEqualTo(roleId);
        sysRolePermissionMapper.deleteByExample(sysRolePermissionExample);

        List<SysRolePermission> sysRolePermissionList = new ArrayList<>();
        for (Long permissionId : permissionIds) {
            SysRolePermission sysRolePermission = new SysRolePermission();
            sysRolePermission.setRoleId(roleId);
            sysRolePermission.setPermissionId(permissionId);
            sysRolePermissionList.add(sysRolePermission);
        }
        return sysRolePermissionCustomMapper.insertList(sysRolePermissionList);
    }

    @Override
    public List<RolePermissionTreeDto> getRolePermissionTree(Long roleId) {
        //找出该角色所有权限id
        List<SysPermission> rolePermissions = getPermissionList(roleId);
        List<Long> permissionIds = rolePermissions.stream().map(SysPermission::getPermissionId).collect(Collectors.toList());
        List<SysPermission> sysPermissionList = sysPermissionMapper.selectByExample(new SysPermissionExample());
        List<RolePermissionTreeDto> result = sysPermissionList.stream().filter(sysPermission -> sysPermission.getPid().equals(0L)).map(sysPermission -> convert(sysPermission, sysPermissionList, permissionIds)).collect(Collectors.toList());
        return result;
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

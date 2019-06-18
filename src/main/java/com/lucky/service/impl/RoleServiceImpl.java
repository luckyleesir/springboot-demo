package com.lucky.service.impl;

import com.github.pagehelper.Page;
import com.lucky.mapper.auto.SysRoleMapper;
import com.lucky.mapper.auto.SysRolePermissionMapper;
import com.lucky.mapper.custom.SysRolePermissionCustomMapper;
import com.lucky.model.*;
import com.lucky.service.RoleService;
import com.lucky.util.PageUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 角色管理
 *
 * @author: lucky
 * @date: 2019/6/12 16:02
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Resource
    private SysRoleMapper sysRoleMapper;
    @Resource
    private SysRolePermissionCustomMapper sysRolePermissionCustomMapper;
    @Resource
    private SysRolePermissionMapper sysRolePermissionMapper;

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
    public List<SysRole> list(Page page) {
        PageUtil.start(page);
        SysRoleExample sysRoleExample = new SysRoleExample();
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
}

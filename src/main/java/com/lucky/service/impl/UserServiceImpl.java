package com.lucky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.lucky.dto.UserDto;
import com.lucky.mapper.auto.*;
import com.lucky.model.*;
import com.lucky.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: lucky
 * @date: 2019/6/11 16:28
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private SysUserMapper sysUserMapper;
    @Resource
    private SysRoleMapper sysRoleMapper;
    @Resource
    private SysUserRoleMapper sysUserRoleMapper;
    @Resource
    private SysPermissionMapper sysPermissionMapper;
    @Resource
    private SysRolePermissionMapper sysRolePermissionMapper;

    @Override
    public List<SysUser> list(Page page) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize(), page.getOrderBy());
        SysUserExample sysUserExample = new SysUserExample();
        List<SysUser> sysUserList = sysUserMapper.selectByExample(sysUserExample);
        return sysUserList;
    }


    @Override
    public void add(UserDto userDto) {
        SysUser sysUser = SysUser.builder().build();
        BeanUtils.copyProperties(userDto, sysUser);
        sysUserMapper.insertSelective(sysUser);
        for (SysRole sysRole : userDto.getSysRoleList()) {
            sysRoleMapper.insertSelective(sysRole);
        }
    }

    @Override
    public UserDto detail(Long userId) {
        UserDto userDto = null;
        SysUser sysUser = sysUserMapper.selectByPrimaryKey(userId);
        if (sysUser != null) {
            userDto = new UserDto();
            BeanUtils.copyProperties(sysUser, userDto);
            SysUserRoleExample sysUserRoleExample = new SysUserRoleExample();
            sysUserRoleExample.createCriteria().andUserIdEqualTo(userId);
            List<SysUserRole> sysUserRoleList = sysUserRoleMapper.selectByExample(sysUserRoleExample);
            List<Long> roleIds = new ArrayList<>();
            for (SysUserRole sysUsersRole : sysUserRoleList) {
                roleIds.add(sysUsersRole.getRoleId());
            }
            //用户角色
            SysRoleExample sysRoleExample = new SysRoleExample();
            sysRoleExample.createCriteria().andRoleIdIn(roleIds);
            List<SysRole> sysRoleList = sysRoleMapper.selectByExample(sysRoleExample);
            userDto.setSysRoleList(sysRoleList);

            // 角色权限
            SysRolePermissionExample sysRolePermissionExample = new SysRolePermissionExample();
            sysRolePermissionExample.createCriteria().andRoleIdIn(roleIds);
            List<SysRolePermission> sysRolePermissionList = sysRolePermissionMapper.selectByExample(sysRolePermissionExample);
            List<Long> permissionIds = new ArrayList<>();
            for (SysRolePermission sysRolePermission : sysRolePermissionList) {
                permissionIds.add(sysRolePermission.getPermissionId());
            }
            SysPermissionExample sysPermissionExample = new SysPermissionExample();
            sysPermissionExample.createCriteria().andPermissionIdIn(permissionIds);
            List<SysPermission> sysPermissionList = sysPermissionMapper.selectByExample(sysPermissionExample);
            userDto.setSysPermissionList(sysPermissionList);
        }
        return userDto;
    }
}

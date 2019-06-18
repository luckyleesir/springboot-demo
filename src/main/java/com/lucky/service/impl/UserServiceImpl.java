package com.lucky.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.crypto.SecureUtil;
import com.github.pagehelper.Page;
import com.lucky.mapper.auto.*;
import com.lucky.mapper.custom.SysUserRoleCustomMapper;
import com.lucky.model.*;
import com.lucky.service.UserService;
import com.lucky.util.PageUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户管理
 *
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
    @Resource
    private SysUserRoleCustomMapper sysUserRoleCustomMapper;

    @Override
    public int register(SysUser sysUser) {
        SysUser user = this.getUserByUsername(sysUser.getUsername());
        if (user != null) {
            throw new RuntimeException("用户名已存在");
        }
        sysUser.setPassword(SecureUtil.md5(sysUser.getPassword()));
        return sysUserMapper.insertSelective(sysUser);
    }

    @Override
    public List<SysUser> list(String name, Page page) {
        PageUtil.start(page);
        SysUserExample sysUserExample = new SysUserExample();
        if (StringUtils.isNotBlank(name)) {
            sysUserExample.createCriteria().andUsernameLike("%" + name + "%");
            sysUserExample.or().andNameLike("%" + name + "%");
            sysUserExample.or().andNickLike("%" + name + "%");
        }
        List<SysUser> sysUserList = sysUserMapper.selectByExample(sysUserExample);
        return sysUserList;
    }


    @Override
    public SysUser detail(Long userId) {
        return sysUserMapper.selectByPrimaryKey(userId);
    }

    @Override
    public int update(Long userId, SysUser sysUser) {
        sysUser.setUserId(userId);
        sysUser.setPassword(null);
        return sysUserMapper.updateByPrimaryKeySelective(sysUser);
    }

    @Override
    public int delete(Long userId) {
        return sysUserMapper.deleteByPrimaryKey(userId);
    }

    @Override
    public int updateUserRole(Long userId, List<Long> roleIds) {
        //删除原来的用户角色关系
        SysUserRoleExample sysUserRoleExample = new SysUserRoleExample();
        sysUserRoleExample.createCriteria().andUserIdEqualTo(userId);
        sysUserRoleMapper.deleteByExample(sysUserRoleExample);

        List<SysUserRole> sysUserRoleList = new ArrayList<>();
        for (Long roleId : roleIds) {
            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setUserId(userId);
            sysUserRole.setRoleId(roleId);
            sysUserRoleList.add(sysUserRole);
        }
        return sysUserRoleCustomMapper.insertList(sysUserRoleList);
    }

    @Override
    public List<SysRole> getRoleList(Long userId) {
        return sysUserRoleCustomMapper.getRoleList(userId);
    }

    @Override
    public List<SysPermission> getPermissionList(Long userId) {
        return sysUserRoleCustomMapper.getPermissionList(userId);
    }

    @Override
    public SysUser getUserByUsername(String username) {
        SysUserExample sysUserExample = new SysUserExample();
        sysUserExample.createCriteria().andUsernameEqualTo(username);
        return CollUtil.getFirst(sysUserMapper.selectByExample(sysUserExample));
    }
}

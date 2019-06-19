package com.lucky.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.github.pagehelper.Page;
import com.lucky.mapper.auto.SysUserMapper;
import com.lucky.mapper.auto.SysUserRoleMapper;
import com.lucky.mapper.custom.SysUserRoleCustomMapper;
import com.lucky.model.*;
import com.lucky.service.UserService;
import com.lucky.util.JwtTokenUtil;
import com.lucky.util.PageUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
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
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private SysUserMapper sysUserMapper;
    @Resource
    private SysUserRoleMapper sysUserRoleMapper;
    @Resource
    private SysUserRoleCustomMapper sysUserRoleCustomMapper;
    @Resource
    private PasswordEncoder passwordEncoder;
    @Resource
    private UserDetailsService userDetailsService;
    @Resource
    private JwtTokenUtil jwtTokenUtil;

    @Override
    public int register(SysUser sysUser) {
        SysUser user = this.getUserByUsername(sysUser.getUsername());
        if (user != null) {
            throw new RuntimeException("用户名已存在");
        }
        sysUser.setPassword(passwordEncoder.encode(sysUser.getPassword()));
        return sysUserMapper.insertSelective(sysUser);
    }

    @Override
    public String login(String username, String password) {
        String token = null;
        //密码需要客户端加密后传递
        try {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if (!passwordEncoder.matches(password, userDetails.getPassword())) {
                throw new BadCredentialsException("密码不正确");
            }
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            token = jwtTokenUtil.generateToken(userDetails);
        } catch (AuthenticationException e) {
            log.warn("登录异常:", e.getMessage());
        }
        return token;
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
        return sysUserMapper.selectByExample(sysUserExample);
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

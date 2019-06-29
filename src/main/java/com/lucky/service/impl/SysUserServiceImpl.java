package com.lucky.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.github.pagehelper.Page;
import com.lucky.common.enums.PermissionType;
import com.lucky.dto.MenuNodeDto;
import com.lucky.dto.PermissionNodeDto;
import com.lucky.mapper.auto.SysPermissionMapper;
import com.lucky.mapper.auto.SysUserMapper;
import com.lucky.mapper.auto.SysUserRoleMapper;
import com.lucky.mapper.custom.SysUserRoleCustomMapper;
import com.lucky.model.*;
import com.lucky.service.SysPermissionService;
import com.lucky.service.SysUserService;
import com.lucky.util.JwtTokenUtil;
import com.lucky.util.PageUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
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
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * 用户管理
 *
 * @author: lucky
 * @date: 2019/6/11 16:28
 */
@Slf4j
@Service
public class SysUserServiceImpl implements SysUserService {

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
    @Resource
    private SysPermissionMapper sysPermissionMapper;

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
            log.warn("登录异常:{}", e.getMessage());
        }
        return token;
    }

    @Override
    public String refreshToken(String oldToken) {
        String token = oldToken.substring(jwtTokenUtil.getTokenHead().length());
        if (jwtTokenUtil.canRefresh(token)) {
            return jwtTokenUtil.refreshToken(token);
        }
        return null;
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
    public int add(SysUser sysUser) {
        SysUser user = this.getUserByUsername(sysUser.getUsername());
        if (user != null) {
            throw new RuntimeException("用户名已存在");
        }
        //后台管理员直接添加
        sysUser.setPassword(passwordEncoder.encode(UUID.randomUUID().toString()));
        return sysUserMapper.insertSelective(sysUser);
    }

    @Override
    public int update(Long userId, SysUser sysUser) {
        sysUser.setUserId(userId);
        sysUser.setPassword(null);
        return sysUserMapper.updateByPrimaryKeySelective(sysUser);
    }

    @Override
    public int delete(List<Long> userIds) {
        SysUserExample sysUserExample = new SysUserExample();
        sysUserExample.createCriteria().andUserIdIn(userIds);
        return sysUserMapper.deleteByExample(sysUserExample);
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

    @Override
    public List<MenuNodeDto> getUserMenu() {
        SysPermissionExample sysPermissionExample = new SysPermissionExample();
        sysPermissionExample.createCriteria().andTypeEqualTo(PermissionType.MENU.getCode());
        List<SysPermission> sysPermissionList = sysPermissionMapper.selectByExample(sysPermissionExample);
        List<MenuNodeDto> menuNodeDtoList = sysPermissionList.stream()
                .filter(sysPermission -> sysPermission.getPid().equals(0L))
                .map(sysPermission -> convert(sysPermission,sysPermissionList))
                .collect(Collectors.toList());
        return menuNodeDtoList;
    }

    /**
     * 将权限转换为带有子级的权限对象
     * 当找不到子级权限的时候map操作不会再递归调用covert
     */
    private MenuNodeDto convert(SysPermission sysPermission, List<SysPermission> sysPermissionList) {
        MenuNodeDto menuNodeDto = new MenuNodeDto();
        menuNodeDto.setHref(sysPermission.getUrl());
        menuNodeDto.setIcon(sysPermission.getIcon());
        menuNodeDto.setMenuId(sysPermission.getPermissionId());
        menuNodeDto.setPid(sysPermission.getPid());
        menuNodeDto.setTitle(sysPermission.getName());
        List<MenuNodeDto> children = sysPermissionList.stream()
                .filter(subPermission -> subPermission.getPid().equals(sysPermission.getPermissionId()))
                .map(subPermission -> convert(subPermission,sysPermissionList))
                .collect(Collectors.toList());
        menuNodeDto.setChildren(children);
        return menuNodeDto;
    }
}

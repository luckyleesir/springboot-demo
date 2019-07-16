package com.lucky.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lucky.common.SysUserHolder;
import com.lucky.common.enums.PermissionType;
import com.lucky.dto.MenuNodeDto;
import com.lucky.dto.SysUserDto;
import com.lucky.mapper.SysPermissionMapper;
import com.lucky.mapper.SysRoleMapper;
import com.lucky.mapper.SysUserMapper;
import com.lucky.mapper.SysUserRoleMapper;
import com.lucky.model.SysPermission;
import com.lucky.model.SysRole;
import com.lucky.model.SysUser;
import com.lucky.model.SysUserRole;
import com.lucky.service.ISysUserRoleService;
import com.lucky.service.ISysUserService;
import com.lucky.util.JwtTokenUtil;
import com.lucky.util.StringUtil;
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
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author lucky
 * @since 2019-07-15
 */
@Slf4j
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    @Resource
    private SysUserRoleMapper sysUserRoleMapper;
    @Resource
    private PasswordEncoder passwordEncoder;
    @Resource
    private UserDetailsService userDetailsService;
    @Resource
    private JwtTokenUtil jwtTokenUtil;
    @Resource
    private SysPermissionMapper sysPermissionMapper;
    @Resource
    private ISysUserRoleService sysUserRoleService;
    @Resource
    private SysRoleMapper sysRoleMapper;

    @Override
    public boolean register(SysUser sysUser) {
        SysUser user = this.getUserByUsername(sysUser.getUsername());
        if (user != null) {
            throw new RuntimeException("用户名已存在");
        }
        sysUser.setPassword(passwordEncoder.encode(sysUser.getPassword()));
        return this.save(sysUser);
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
    public IPage<SysUser> list(Page<SysUser> page, String name) {
        LambdaQueryWrapper<SysUser> sysUserLambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(name)) {
            sysUserLambdaQueryWrapper.like(SysUser::getUsername, name);
            sysUserLambdaQueryWrapper.or().like(SysUser::getName, name);
            sysUserLambdaQueryWrapper.or().like(SysUser::getNick, name);
        }
        return this.page(page, sysUserLambdaQueryWrapper);
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
    public SysUser detail(Long userId) {
        return this.getById(userId);
    }

    @Override
    public boolean add(SysUserDto sysUserDto) {
        SysUser user = this.getUserByUsername(sysUserDto.getUsername());
        if (user != null) {
            throw new RuntimeException("用户名已存在");
        }
        if (StringUtils.isBlank(sysUserDto.getRoleId())) {
            throw new RuntimeException("请选择角色");
        }
        //后台管理员直接添加
        sysUserDto.setPassword(passwordEncoder.encode(UUID.randomUUID().toString()));
        this.save(sysUserDto);
        return this.updateUserRole(sysUserDto.getUserId(), StringUtil.getList(sysUserDto.getRoleId()));
    }

    @Override
    public boolean update(Long userId, SysUserDto sysUserDto) {
        sysUserDto.setUserId(userId);
        sysUserDto.setPassword(null);
        this.updateById(sysUserDto);
        return this.updateUserRole(sysUserDto.getUserId(), StringUtil.getList(sysUserDto.getRoleId()));
    }

    @Override
    public int delete(List<Long> userIds) {
        return this.baseMapper.deleteBatchIds(userIds);
    }

    @Override
    public boolean updateUserRole(Long userId, List<Long> roleIds) {
        //删除原来的用户角色关系
        LambdaQueryWrapper<SysUserRole> sysUserRoleLambdaQueryWrapper = new LambdaQueryWrapper<>();
        sysUserRoleLambdaQueryWrapper.eq(SysUserRole::getUserId, userId);
        sysUserRoleMapper.delete(sysUserRoleLambdaQueryWrapper);

        List<SysUserRole> sysUserRoleList = new ArrayList<>();
        for (Long roleId : roleIds) {
            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setUserId(userId);
            sysUserRole.setRoleId(roleId);
            sysUserRoleList.add(sysUserRole);
        }
        return sysUserRoleService.saveBatch(sysUserRoleList);
    }

    @Override
    public List<SysRole> getRoleList(Long userId) {
        return sysRoleMapper.getUserRoleList(userId);
    }

    @Override
    public List<SysPermission> getPermissionList(Long userId) {
        return sysPermissionMapper.getUserPermissionList(userId);
    }

    @Override
    public SysUser getUserByUsername(String username) {
        LambdaQueryWrapper<SysUser> sysUserLambdaQueryWrapper = new LambdaQueryWrapper<>();
        sysUserLambdaQueryWrapper.eq(SysUser::getUsername, username);
        return this.getOne(sysUserLambdaQueryWrapper);
    }

    @Override
    public List<MenuNodeDto> getUserMenu() {
        log.info("{}", SysUserHolder.get());
        LambdaQueryWrapper<SysPermission> sysPermissionLambdaQueryWrapper = new LambdaQueryWrapper<>();
        sysPermissionLambdaQueryWrapper.eq(SysPermission::getType, PermissionType.MENU.getCode());
        List<SysPermission> sysPermissionList = sysPermissionMapper.selectList(sysPermissionLambdaQueryWrapper);
        return sysPermissionList.stream().filter(sysPermission -> sysPermission.getPid().equals(0L)).map(sysPermission -> convert(sysPermission, sysPermissionList)).collect(Collectors.toList());
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
        List<MenuNodeDto> children = sysPermissionList.stream().filter(subPermission -> subPermission.getPid().equals(sysPermission.getPermissionId())).map(subPermission -> convert(subPermission, sysPermissionList)).collect(Collectors.toList());
        menuNodeDto.setChildren(children);
        return menuNodeDto;
    }

    @Override
    public IPage<SysUserDto> list(Page<SysUserDto> page, SysUserDto sysUserDto) {
        return this.baseMapper.list(page, sysUserDto);
    }

    @Override
    public boolean changePassword(String username, String ordPassword, String password) {
        SysUser sysUser = this.getUserByUsername(username);
        if (sysUser == null) {
            throw new RuntimeException("账号不存在");
        }
        if (!passwordEncoder.matches(ordPassword, sysUser.getPassword())) {
            throw new RuntimeException("密码不正确");
        }
        sysUser.setPassword(passwordEncoder.encode(password));
        return this.updateById(sysUser);
    }
}

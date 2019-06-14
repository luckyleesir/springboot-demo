package com.lucky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.lucky.dto.UserDto;
import com.lucky.mapper.auto.SysRoleMapper;
import com.lucky.mapper.auto.SysUserMapper;
import com.lucky.model.SysRole;
import com.lucky.model.SysUser;
import com.lucky.model.SysUserExample;
import com.lucky.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
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

    @Override
    public List<SysUser> list(Page page) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize(), page.getOrderBy());
        SysUserExample sysUserExample = new SysUserExample();
        List<SysUser> sysUserList = sysUserMapper.selectByExample(sysUserExample);
        return sysUserList;
    }


    @Transactional(rollbackFor = {Error.class, Exception.class})
    @Override
    public void add(UserDto userDto) {
        SysUser sysUser = SysUser.builder().build();
        BeanUtils.copyProperties(userDto, sysUser);
        sysUserMapper.insertSelective(sysUser);
        for (SysRole sysRole : userDto.getSysRoleList()) {
            sysRoleMapper.insertSelective(sysRole);
        }
    }
}

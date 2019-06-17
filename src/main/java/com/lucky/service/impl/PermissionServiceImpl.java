package com.lucky.service.impl;

import com.github.pagehelper.Page;
import com.lucky.dto.PermissionNodeDto;
import com.lucky.mapper.auto.SysPermissionMapper;
import com.lucky.model.SysPermission;
import com.lucky.model.SysPermissionExample;
import com.lucky.service.PermissionService;
import com.lucky.util.PageUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: lucky
 * @date: 2019/6/12 16:03
 */
@Slf4j
@Service
public class PermissionServiceImpl implements PermissionService {

    @Resource
    private SysPermissionMapper sysPermissionMapper;


    @Override
    public int add(SysPermission sysPermission) {
        return sysPermissionMapper.insertSelective(sysPermission);
    }

    @Override
    public int delete(List<Long> permissionIds) {
        SysPermissionExample sysPermissionExample = new SysPermissionExample();
        sysPermissionExample.createCriteria().andPermissionIdIn(permissionIds);
        return sysPermissionMapper.deleteByExample(sysPermissionExample);
    }

    @Override
    public SysPermission detail(Long permissionId) {
        return sysPermissionMapper.selectByPrimaryKey(permissionId);
    }

    @Override
    public int update(Long permissionId, SysPermission sysPermission) {
        sysPermission.setPermissionId(permissionId);
        return sysPermissionMapper.updateByPrimaryKeySelective(sysPermission);
    }

    @Override
    public List<SysPermission> list(Page page) {
        PageUtil.start(page);
        SysPermissionExample sysPermissionExample = new SysPermissionExample();
        return sysPermissionMapper.selectByExample(sysPermissionExample);
    }

    @Override
    public List<PermissionNodeDto> treeList() {
        List<SysPermission> sysPermissionList = sysPermissionMapper.selectByExample(new SysPermissionExample());
        List<PermissionNodeDto> result = sysPermissionList.stream()
                .filter(sysPermission -> sysPermission.getPid().equals(0L))
                .map(sysPermission -> convert(sysPermission,sysPermissionList))
                .collect(Collectors.toList());
        return result;
    }

    /**
     * 将权限转换为带有子级的权限对象
     * 当找不到子级权限的时候map操作不会再递归调用covert
     */
    private PermissionNodeDto convert(SysPermission sysPermission, List<SysPermission> sysPermissionList) {
        PermissionNodeDto permissionNodeDto = new PermissionNodeDto();
        BeanUtils.copyProperties(sysPermission,permissionNodeDto);
        List<PermissionNodeDto> children = sysPermissionList.stream()
                .filter(subPermission -> subPermission.getPid().equals(sysPermission.getPermissionId()))
                .map(subPermission -> convert(subPermission,sysPermissionList))
                .collect(Collectors.toList());
        permissionNodeDto.setChildren(children);
        return permissionNodeDto;
    }
}

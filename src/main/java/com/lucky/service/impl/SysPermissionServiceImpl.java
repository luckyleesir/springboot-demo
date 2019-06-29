package com.lucky.service.impl;

import com.github.pagehelper.Page;
import com.lucky.dto.PermissionNodeDto;
import com.lucky.mapper.auto.SysPermissionMapper;
import com.lucky.model.SysPermission;
import com.lucky.model.SysPermissionExample;
import com.lucky.service.SysPermissionService;
import com.lucky.util.PageUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
public class SysPermissionServiceImpl implements SysPermissionService {

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
    public List<SysPermission> list(String name, Page page) {
        PageUtil.start(page);
        SysPermissionExample sysPermissionExample = new SysPermissionExample();
        if (StringUtils.isNotBlank(name)) {
            String like = "%" + name + "%";
            sysPermissionExample.createCriteria().andNameLike(like);
            sysPermissionExample.or().andDescriptionLike(like);
            sysPermissionExample.or().andValueLike(like);
        }
        return sysPermissionMapper.selectByExample(sysPermissionExample);
    }

    @Override
    public List<PermissionNodeDto> treeList(SysPermission selectParam) {
       SysPermissionExample sysPermissionExample =   new SysPermissionExample();
        if (selectParam!=null){
            if (selectParam.getType()!=null){
                sysPermissionExample.createCriteria().andTypeEqualTo(selectParam.getType());
            }
            if (selectParam.getStatus()!=null){
                sysPermissionExample.createCriteria().andStatusEqualTo(selectParam.getStatus());
            }
        }
        List<SysPermission> sysPermissionList = sysPermissionMapper.selectByExample(sysPermissionExample);
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

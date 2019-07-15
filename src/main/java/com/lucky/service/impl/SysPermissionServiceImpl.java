package com.lucky.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lucky.dto.PermissionNodeDto;
import com.lucky.mapper.SysPermissionMapper;
import com.lucky.model.SysPermission;
import com.lucky.service.ISysPermissionService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 权限表 服务实现类
 * </p>
 *
 * @author lucky
 * @since 2019-07-15
 */
@Slf4j
@Service
public class SysPermissionServiceImpl extends ServiceImpl<SysPermissionMapper, SysPermission> implements ISysPermissionService {

    @Override
    public boolean add(SysPermission sysPermission) {
        return this.save(sysPermission);
    }

    @Override
    public boolean delete(List<Long> permissionIds) {
        return this.removeByIds(permissionIds);
    }

    @Override
    public SysPermission detail(Long permissionId) {
        return this.getById(permissionId);
    }

    @Override
    public boolean update(Long permissionId, SysPermission sysPermission) {
        sysPermission.setPermissionId(permissionId);
        return this.updateById(sysPermission);
    }

    @Override
    public IPage<SysPermission> list(Page<SysPermission> page, String name) {
        LambdaQueryWrapper<SysPermission> sysPermissionLambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(name)) {
            sysPermissionLambdaQueryWrapper.like(SysPermission::getName, name).or().like(SysPermission::getValue, name).or().like(SysPermission::getDescription, name);
        }
        return this.page(page, sysPermissionLambdaQueryWrapper);
    }

    @Override
    public List<PermissionNodeDto> treeList(SysPermission selectParam) {
        LambdaQueryWrapper<SysPermission> sysPermissionLambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (selectParam != null) {
            if (selectParam.getType() != null) {
                sysPermissionLambdaQueryWrapper.eq(SysPermission::getType, selectParam.getType());
            }
            if (selectParam.getStatus() != null) {
                sysPermissionLambdaQueryWrapper.eq(SysPermission::getStatus, selectParam.getStatus());
            }
        }
        List<SysPermission> sysPermissionList = this.list(sysPermissionLambdaQueryWrapper);
        return sysPermissionList.stream().filter(sysPermission -> sysPermission.getPid().equals(0L)).map(sysPermission -> convert(sysPermission, sysPermissionList)).collect(Collectors.toList());
    }

    /**
     * 将权限转换为带有子级的权限对象
     * 当找不到子级权限的时候map操作不会再递归调用covert
     */
    private PermissionNodeDto convert(SysPermission sysPermission, List<SysPermission> sysPermissionList) {
        PermissionNodeDto permissionNodeDto = new PermissionNodeDto();
        BeanUtils.copyProperties(sysPermission, permissionNodeDto);
        List<PermissionNodeDto> children = sysPermissionList.stream().filter(subPermission -> subPermission.getPid().equals(sysPermission.getPermissionId())).map(subPermission -> convert(subPermission, sysPermissionList)).collect(Collectors.toList());
        permissionNodeDto.setChildren(children);
        return permissionNodeDto;
    }
}

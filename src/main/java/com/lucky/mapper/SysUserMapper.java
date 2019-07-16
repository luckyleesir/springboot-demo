package com.lucky.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lucky.dto.SysUserDto;
import com.lucky.model.SysUser;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author lucky
 * @since 2019-07-15
 */
public interface SysUserMapper extends BaseMapper<SysUser> {

    /**
     * 用户列表查询
     *
     * @param page       分页
     * @param sysUserDto
     * @return
     */
    IPage<SysUserDto> list(Page page, @Param("sysUserDto") SysUserDto sysUserDto);
}

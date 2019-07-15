package com.lucky.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lucky.model.SysRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 角色表 Mapper 接口
 * </p>
 *
 * @author lucky
 * @since 2019-07-15
 */
public interface SysRoleMapper extends BaseMapper<SysRole> {
    /**
     * 获取指定用户角色
     *
     * @param userId 用户id
     * @return 角色列表
     */
    List<SysRole> getUserRoleList(@Param("userId") Long userId);
}

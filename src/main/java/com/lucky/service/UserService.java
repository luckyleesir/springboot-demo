package com.lucky.service;

import com.github.pagehelper.Page;
import com.lucky.dto.UserDto;
import com.lucky.model.SysUser;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 用户管理
 */
public interface UserService {

    /**
     * 分页查询用户
     * @param page
     * @return
     */
    List<SysUser> list(Page page);

    /**
     * 添加用户
     *
     * @param userDto
     * @throws Exception
     */
    @Transactional(rollbackFor = {Error.class, Exception.class})
    void add(UserDto userDto) throws Exception;

    /**
     * 用户详情
     */
    UserDto detail(Long userId);
}

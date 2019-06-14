package com.lucky.service;

import com.github.pagehelper.Page;
import com.lucky.dto.UserDto;
import com.lucky.model.SysUser;

import java.util.List;

/**
 * 用户管理
 */
public interface UserService {

    List<SysUser> list(Page page);

    /**
     * 添加用户
     *
     * @param userDto
     * @throws Exception
     */
    void add(UserDto userDto) throws Exception;
}

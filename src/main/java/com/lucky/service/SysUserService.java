package com.lucky.service;

import com.github.pagehelper.Page;
import com.lucky.dto.MenuNodeDto;
import com.lucky.model.SysPermission;
import com.lucky.model.SysRole;
import com.lucky.model.SysUser;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 用户管理
 *
 * @author lucky
 */
public interface SysUserService {

    /**
     * 用户注册
     *
     * @param sysUser 注册信息
     * @return
     */
    int register(SysUser sysUser);

    /**
     * 用户登录
     *
     * @param username 用户名
     * @param password 密码
     * @return 登录成功返回token
     */
    String login(String username, String password);

    /**
     * 刷新token
     *
     * @param oldToken 旧token
     * @return 新token
     */
    String refreshToken(String oldToken);

    /**
     * 分页查询用户
     *
     * @param name 用户名/昵称/姓名
     * @param page 分页
     * @return 用户列表
     */
    List<SysUser> list(String name, Page page);

    /**
     * 用户详情
     *
     * @param userId 用户id
     * @return 用户信息
     */
    SysUser detail(Long userId);

    /**
     * 添加用户
     * @param sysUser 用户信息
     * @return 添加条数
     */
    int add(SysUser sysUser);

    /**
     * 修改用户信息
     *
     * @param userId  用户id
     * @param sysUser 用户信息
     * @return 成功条数
     */
    @Transactional(rollbackFor = {Error.class, Exception.class})
    int update(Long userId, SysUser sysUser);

    /**
     * 批量删除用户
     *
     * @param userIds 用户id
     * @return 删除条数
     */
    @Transactional(rollbackFor = {Error.class, Exception.class})
    int delete(List<Long> userIds);

    /**
     * 给用户分配角色
     *
     * @param userId  用户id
     * @param roleIds 角色id数组
     * @return 分配成功条数
     */
    @Transactional(rollbackFor = {Error.class, Exception.class})
    int updateUserRole(Long userId, List<Long> roleIds);

    /**
     * 获取指定用户角色
     *
     * @param userId 用户id
     * @return 角色列表
     */
    List<SysRole> getRoleList(Long userId);

    /**
     * 获取指定用户权限
     *
     * @param userId 用户id
     * @return 权限列表
     */
    List<SysPermission> getPermissionList(Long userId);

    /**
     * 根据username查询用户
     *
     * @param username 用户名
     * @return 用户
     */
    SysUser getUserByUsername(String username);

    /**
     * 获取用户菜单
     */
    List<MenuNodeDto> getUserMenu();
}

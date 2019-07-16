package com.lucky.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lucky.dto.MenuNodeDto;
import com.lucky.dto.SysUserDto;
import com.lucky.model.SysPermission;
import com.lucky.model.SysRole;
import com.lucky.model.SysUser;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author lucky
 * @since 2019-07-15
 */
public interface ISysUserService extends IService<SysUser> {

    /**
     * 用户注册
     *
     * @param sysUser 注册信息
     * @return 成功或失败
     */
    boolean register(SysUser sysUser);

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
    IPage<SysUser> list(Page<SysUser> page, String name);

    /**
     * 用户详情
     *
     * @param userId 用户id
     * @return 用户信息
     */
    SysUser detail(Long userId);

    /**
     * 添加用户
     *
     * @param sysUserDto 用户信息
     * @return 成功或失败
     */
    boolean add(SysUserDto sysUserDto);

    /**
     * 修改用户信息
     *
     * @param userId     用户id
     * @param sysUserDto 用户信息
     * @return 成功或失败
     */
    @Transactional(rollbackFor = {Error.class, Exception.class})
    boolean update(Long userId, SysUserDto sysUserDto);

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
     * @return 成功或失败
     */
    @Transactional(rollbackFor = {Error.class, Exception.class})
    boolean updateUserRole(Long userId, List<Long> roleIds);

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
     * @return 用户详情
     */
    SysUser getUserByUsername(String username);

    /**
     * 获取用户菜单
     *
     * @return 用户菜单树
     */
    List<MenuNodeDto> getUserMenu();

    /**
     * 用户列表查询（关联角色）
     *
     * @param page       分页
     * @param sysUserDto 查询条件
     * @return 用户分页列表
     */
    IPage<SysUserDto> list(Page<SysUserDto> page, SysUserDto sysUserDto);

    /**
     * 修改密码
     *
     * @param username    用户名
     * @param ordPassword 旧密码
     * @param password    密码
     * @return 成功或失败
     */
    boolean changePassword(String username, String ordPassword, String password);
}

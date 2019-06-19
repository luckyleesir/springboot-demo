package com.lucky.config.shiro;

import cn.hutool.core.collection.CollUtil;
import com.lucky.model.SysPermission;
import com.lucky.model.SysRole;
import com.lucky.model.SysUser;
import com.lucky.service.UserService;
import com.lucky.util.JwtUtil;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author: lucky
 * @date: 2019/6/18 11:27
 */
public class MyShiroRealm extends AuthorizingRealm {

    @Resource
    private UserService userService;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    /**
     * 授权
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SysUser sysUser = (SysUser) principalCollection.getPrimaryPrincipal();
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        if (sysUser != null) {
            //角色
            List<SysRole> sysRoleList = userService.getRoleList(sysUser.getUserId());
            if (CollUtil.isNotEmpty(sysRoleList)) {
                for (SysRole sysRole : sysRoleList) {
                    simpleAuthorizationInfo.addRole(sysRole.getName());
                }
            }
            //权限
            List<SysPermission> sysPermissionList = userService.getPermissionList(sysUser.getUserId());
            if (CollUtil.isNotEmpty(sysPermissionList)) {
                for (SysPermission sysPermission : sysPermissionList) {
                    simpleAuthorizationInfo.addStringPermission(sysPermission.getValue());
                }
            }
        }
        return simpleAuthorizationInfo;
    }


    /**
     * 认证登录
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String token = (String) authenticationToken.getCredentials();
        // 解密获得username，用于和数据库进行对比
        String username = JwtUtil.getUsername(token);
        if (username == null) {
            throw new AuthenticationException("token invalid");
        }
        //获取用户信息
        SysUser sysUser = userService.getUserByUsername(username);
        if (sysUser == null) {
            //这里返回后会报出对应异常
            return null;
        }

        if (!JwtUtil.verify(token, username, sysUser.getPassword())) {
            throw new AuthenticationException("Username or password error");
        }
        //这里验证authenticationToken和simpleAuthenticationInfo的信息
        return new SimpleAuthenticationInfo(sysUser, sysUser.getPassword(), getName());

    }
}

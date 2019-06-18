package com.lucky.config.shiro;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * shiro权限配置
 *
 * @author: lucky
 * @date: 2019/6/18 13:54
 */
@Configuration
public class ShiroConfig {

    /**
     * 将自己的验证方式加入容器
     *
     * @return
     */
    @Bean
    public MyShiroRealm myShiroRealm() {
        MyShiroRealm myShiroRealm = new MyShiroRealm();
        return myShiroRealm;
    }

    /**
     * 权限管理，配置主要是Realm的管理认证
     *
     * @return
     */
    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(myShiroRealm());
        return securityManager;
    }

    /**
     * Filter工厂，设置对应的过滤条件和跳转条件
     *
     * @param securityManager
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        Map<String, String> map = new HashMap<>();
        //登出
        map.put("/user/logout", "logout");
        //对所有用户认证
        map.put("/**", "authc");
        map.put("/swagger-ui.html", "anon");
        map.put("/swagger-resources/**", "anon");
        map.put("/v2/api-docs/**", "anon");
        map.put("/webjars/springfox-swagger-ui/**", "anon");
        //登录
        shiroFilterFactoryBean.setLoginUrl("/user/login");
        //首页
        shiroFilterFactoryBean.setSuccessUrl("/user/list");
        //错误页面，认证不通过跳转
        shiroFilterFactoryBean.setUnauthorizedUrl("/error");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
        return shiroFilterFactoryBean;
    }

    /**
     * 开启shiro aop注解支持.
     *
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    /**
     * 加密算法
     *
     * @return
     */
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        //采用MD5 进行加密
        hashedCredentialsMatcher.setHashAlgorithmName("MD5");
        //加密次数
        hashedCredentialsMatcher.setHashIterations(1);
        return hashedCredentialsMatcher;
    }

    /**
     * 配置realm，用于认证和授权
     *
     * @param hashedCredentialsMatcher
     * @return
     */
    @Bean
    public AuthorizingRealm shiroRealm(HashedCredentialsMatcher hashedCredentialsMatcher) {
        MyShiroRealm shiroRealm = new MyShiroRealm();
        //校验密码用到的算法
        shiroRealm.setCredentialsMatcher(hashedCredentialsMatcher);
        return shiroRealm;
    }

    /***
     * 配置thymeleaf和shiro的依赖
     * @return
     */
    @Bean
    public ShiroDialect createDialect() {
        return new ShiroDialect();
    }
}

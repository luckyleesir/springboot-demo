package com.lucky.common;

import com.lucky.model.SysUser;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: lucky
 * @date: 2019/7/2 19:32
 */
@Slf4j
public class SysUserHolder {

    private static ThreadLocal<SysUser> local = new ThreadLocal<>();

    /**
     * 赋值
     *
     * @param sysUser
     */
    public static void set(SysUser sysUser) {
        local.set(sysUser);
        log.info("用户信息{}", sysUser);
    }

    /**
     * 取值
     *
     * @return
     */
    public static SysUser get() {
        log.info("当前线程id：{}", Thread.currentThread().getName());
        return local.get();
    }

    /**
     * 移除
     */
    public static void remove() {
        local.remove();
    }

}

package com.lucky.util;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * Redis工具
 *
 * @author: lucky
 * @date: 2019/6/20 11:05
 */
@Component
public class RedisUtil {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 存储数据
     *
     * @param key
     * @param value
     */
    public void set(String key, String value) {
        stringRedisTemplate.opsForValue().set(key, value);
    }

    /**
     * 获取数据
     *
     * @param key
     * @return
     */
    public String get(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    /**
     * 设置失效时间
     *
     * @param key
     * @param expire
     * @return
     */
    public boolean expire(String key, long expire) {
        return stringRedisTemplate.expire(key, expire, TimeUnit.SECONDS);
    }

    /**
     * 删除
     *
     * @param key
     */
    public void remove(String key) {
        stringRedisTemplate.delete(key);
    }

    /**
     * 自增
     *
     * @param key
     * @param delta
     * @return
     */
    public Long increment(String key, long delta) {
        return stringRedisTemplate.opsForValue().increment(key, delta);
    }

}

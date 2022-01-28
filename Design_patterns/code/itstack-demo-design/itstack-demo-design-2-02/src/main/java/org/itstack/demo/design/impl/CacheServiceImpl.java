package org.itstack.demo.design.impl;/**
 * Project: itstack-demo-design
 * Package: org.itstack.demo.design.impl
 * Version: 1.0
 * Created by ljy on 2022-1-26 15:13
 */

import org.itstack.demo.design.CacheService;
import org.itstack.demo.design.RedisUtils;

import java.util.concurrent.TimeUnit;

/**
 * @ClassName CacheServiceImpl
 * @Author: ljy on 2022-1-26 15:13
 * @Version: 1.0
 * @Description:
 * 内存接口的实现类
 */
public class CacheServiceImpl implements CacheService {
    private RedisUtils redisUtils = new RedisUtils();

    public String get(String key) {
        return redisUtils.get(key);
    }

    public void set(String key, String value) {
        redisUtils.set(key, value);
    }

    public void set(String key, String value, long timeout, TimeUnit timeUnit) {
        redisUtils.set(key, value, timeout, timeUnit);
    }

    public void del(String key) {
        redisUtils.del(key);
    }
}

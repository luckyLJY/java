package org.itstack.demo.design.cuisine.impl;/**
 * Project: itstack-demo-design
 * Package: org.itstack.demo.design.cuisine.impl
 * Version: 1.0
 * Created by ljy on 2022-1-10 12:00
 */

import org.itstack.demo.design.CacheService;
import org.itstack.demo.design.RedisUtils;

import java.util.concurrent.TimeUnit;

/**
 * @ClassName CacheServiceImpl
 * @Author: ljy on 2022-1-10 12:00
 * @Version: 1.0
 * @Description
 */
public class CacheServiceImpl implements CacheService {

    private RedisUtils redisUtils;
    @Override
    public String get(String key) {
        return redisUtils.get(key);
    }

    @Override
    public void set(String key, String value) {
        redisUtils.set(key,value);
    }

    @Override
    public void set(String key, String value, long timeout, TimeUnit timeUnit) {
        redisUtils.set(key,value,timeout,timeUnit);
    }

    @Override
    public void del(String key) {
        redisUtils.del(key);
    }
}

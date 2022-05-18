package com.macro.mall.tiny.service;/**
 * Project: mall-tiny-delay
 * Package: com.macro.mall.tiny.service
 * Version: 1.0
 * Created by ljy on 2022-4-26 16:57
 */

/**
 * @ClassName RedisService
 * @Author: ljy on 2022-4-26 16:57
 * @Version: 1.0
 * @Description:
 */
/**
 * redis操作Service,
 * 对象和数组都以json形式进行存储
 * Created by macro on 2018/8/7.
 */
public interface RedisService {
    /**
     * 存储数据
     */
    void set(String key, String value);

    /**
     * 获取数据
     */
    String get(String key);

    /**
     * 设置超期时间
     */
    boolean expire(String key, long expire);

    /**
     * 删除数据
     */
    void remove(String key);

    /**
     * 自增操作
     * @param delta 自增步长
     */
    Long increment(String key, long delta);

}

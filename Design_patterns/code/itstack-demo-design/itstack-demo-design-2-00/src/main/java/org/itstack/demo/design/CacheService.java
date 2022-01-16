package org.itstack.demo.design;/**
 * Project: itstack-demo-design
 * Package: org.itstack.demo.design
 * Version: 1.0
 * Created by ljy on 2022-1-10 11:50
 */

import java.util.concurrent.TimeUnit;

/**
 * @ClassName CacheService
 * @Author: ljy on 2022-1-10 11:50
 * @Version: 1.0
 * @Description：
 * 定义内存接口
 */
public interface CacheService {
    String get(final String key);

    void set(String key, String value);

    void set(String key, String value, long timeout, TimeUnit timeUnit);

    void del(String key);
}

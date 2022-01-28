package org.itstack.demo.design.factory;/**
 * Project: itstack-demo-design
 * Package: org.itstack.demo.design.factory
 * Version: 1.0
 * Created by ljy on 2022-1-26 15:28
 */

import java.util.concurrent.TimeUnit;

/**
 * @ClassName
 * @Author: ljy on 2022-1-26 15:28
 * @Version: 1.0
 * @Description
 */
public interface ICacheAdapter {
    String get(String key);

    void set(String key, String value);

    void set(String key, String value, long timeout, TimeUnit timeUnit);

    void del(String key);
}

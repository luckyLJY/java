package org.itstack.demo.design.factory.impl;/**
 * Project: itstack-demo-design
 * Package: org.itstack.demo.design.factory.impl
 * Version: 1.0
 * Created by ljy on 2022-1-26 15:59
 */

import org.itstack.demo.design.factory.ICacheAdapter;
import org.itstack.demo.design.matter.EGM;

import java.util.concurrent.TimeUnit;

/**
 * @ClassName EGMCacheAdapter
 * @Author: ljy on 2022-1-26 15:59
 * @Version: 1.0
 * @Description:
 * 多节点集群装饰器
 */
public class EGMCacheAdapter implements ICacheAdapter {

    private EGM egm = new EGM();

    public String get(String key) {
        return egm.gain(key);
    }

    public void set(String key, String value) {
        egm.set(key, value);
    }

    public void set(String key, String value, long timeout, TimeUnit timeUnit) {
        egm.setEx(key, value, timeout, timeUnit);
    }

    public void del(String key) {
        egm.delete(key);
    }
}
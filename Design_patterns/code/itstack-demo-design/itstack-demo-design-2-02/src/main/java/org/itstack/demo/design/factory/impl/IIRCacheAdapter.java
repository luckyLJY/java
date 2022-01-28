package org.itstack.demo.design.factory.impl;/**
 * Project: itstack-demo-design
 * Package: org.itstack.demo.design.factory
 * Version: 1.0
 * Created by ljy on 2022-1-26 15:47
 */

import org.itstack.demo.design.factory.ICacheAdapter;
import org.itstack.demo.design.matter.IIR;

import java.util.concurrent.TimeUnit;

/**
 * @ClassName IIRCacheAdapter
 * @Author: ljy on 2022-1-26 15:47
 * @Version: 1.0
 * @Description:
 * 单个集群装饰器
 */
public class IIRCacheAdapter implements ICacheAdapter {

    private IIR iir = new IIR();

    @Override
    public String get(String key) {
        return iir.get(key);
    }

    @Override
    public void set(String key, String value) {
        iir.set(key,value);
    }

    @Override
    public void set(String key, String value, long timeout, TimeUnit timeUnit) {
        iir.setExpire(key,value,timeout,timeUnit);
    }

    @Override
    public void del(String key) {
        iir.del(key);
    }
}

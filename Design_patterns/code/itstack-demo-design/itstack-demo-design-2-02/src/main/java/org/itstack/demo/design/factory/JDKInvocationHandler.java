package org.itstack.demo.design.factory;/**
 * Project: itstack-demo-design
 * Package: org.itstack.demo.design.factory
 * Version: 1.0
 * Created by ljy on 2022-1-26 16:04
 */

import org.itstack.demo.design.util.ClassLoaderUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @ClassName JDKInvocationHandler
 * @Author: ljy on 2022-1-26 16:04
 * @Version: 1.0
 * @Description:
 * 工厂实现
 */
public class JDKInvocationHandler implements InvocationHandler {
    private ICacheAdapter cacheAdapter;

    public JDKInvocationHandler(ICacheAdapter cacheAdapter) {
        this.cacheAdapter = cacheAdapter;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return ICacheAdapter.class.getMethod(method.getName(), ClassLoaderUtils.getClazzByArgs(args)).invoke(cacheAdapter,args);
    }
}

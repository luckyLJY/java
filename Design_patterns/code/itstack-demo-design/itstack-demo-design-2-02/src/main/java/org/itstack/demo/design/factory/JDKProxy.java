package org.itstack.demo.design.factory;/**
 * Project: itstack-demo-design
 * Package: org.itstack.demo.design.factory
 * Version: 1.0
 * Created by ljy on 2022-1-26 15:24
 */

import java.lang.reflect.Proxy;

/**
 * @ClassName JDKProxy
 * @Author: ljy on 2022-1-26 15:24
 * @Version: 1.0
 * @Description:
 * 抽象工厂
 */
public class JDKProxy {
    public static <T> T getProxy(Class<T> interfaceClass,ICacheAdapter cacheAdapter) throws Exception{
        JDKInvocationHandler handler = new JDKInvocationHandler(cacheAdapter);
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Class<?>[] classes = interfaceClass.getInterfaces();
        return (T) Proxy.newProxyInstance(classLoader,new Class[]{classes[0]},handler);
    }
}























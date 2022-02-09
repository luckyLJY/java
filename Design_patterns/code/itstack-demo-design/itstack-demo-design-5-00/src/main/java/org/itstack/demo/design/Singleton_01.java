package org.itstack.demo.design;/**
 * Project: itstack-demo-design
 * Package: org.itstack.demo.design
 * Version: 1.0
 * Created by ljy on 2022-2-9 15:02
 */

/**
 * @ClassName Singleton_01
 * @Author: ljy on 2022-2-9 15:02
 * @Version: 1.0
 * @Description:1.懒汉模式(线程不安全)
 */
public class Singleton_01 {
    private static Singleton_01 instance;

    private Singleton_01() {
    }

    public static Singleton_01 getInstance(){
        if (null != instance) return instance;
        return new Singleton_01();
    }
}

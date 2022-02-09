package org.itstack.demo.design;/**
 * Project: itstack-demo-design
 * Package: org.itstack.demo.design
 * Version: 1.0
 * Created by ljy on 2022-2-9 15:05
 */

/**
 * @ClassName Singleton_03
 * @Author: ljy on 2022-2-9 15:05
 * @Version: 1.0
 * @Description:饿汉模式(线程安全)
 */
public class Singleton_03 {
    private static Singleton_03 instance = new Singleton_03();

    private Singleton_03() {
    }

    public static Singleton_03 getInstance() {
        return instance;
    }
}

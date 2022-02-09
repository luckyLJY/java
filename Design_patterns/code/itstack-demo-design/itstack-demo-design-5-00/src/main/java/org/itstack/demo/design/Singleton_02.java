package org.itstack.demo.design;/**
 * Project: itstack-demo-design
 * Package: org.itstack.demo.design
 * Version: 1.0
 * Created by ljy on 2022-2-9 15:04
 */

/**
 * @ClassName Singleton_02
 * @Author: ljy on 2022-2-9 15:04
 * @Version: 1.0
 * @Description:懒汉模式(线程安全)
 */
public class Singleton_02 {

    private static Singleton_02 instance;

    private Singleton_02() {
    }

    public static synchronized Singleton_02 getInstance(){
        if (null != instance) return instance;
        return new Singleton_02();
    }
}

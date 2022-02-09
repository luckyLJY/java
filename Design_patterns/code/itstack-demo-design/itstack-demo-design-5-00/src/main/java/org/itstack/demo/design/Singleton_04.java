package org.itstack.demo.design;/**
 * Project: itstack-demo-design
 * Package: org.itstack.demo.design
 * Version: 1.0
 * Created by ljy on 2022-2-9 15:06
 */

/**
 * @ClassName Singleton_04
 * @Author: ljy on 2022-2-9 15:06
 * @Version: 1.0
 * @Description:使用类的内部类(线程安全)
 * 推荐使用
 */
public class Singleton_04 {
    private static class SingletonHolder {
        private static Singleton_04 instance = new Singleton_04();
    }

    private Singleton_04() {
    }

    public static Singleton_04 getInstance() {
        return SingletonHolder.instance;
    }
}

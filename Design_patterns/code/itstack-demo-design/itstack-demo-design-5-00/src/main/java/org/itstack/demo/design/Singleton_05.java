package org.itstack.demo.design;/**
 * Project: itstack-demo-design
 * Package: org.itstack.demo.design
 * Version: 1.0
 * Created by ljy on 2022-2-9 15:08
 */

/**
 * @ClassName Singleton_05
 * @Author: ljy on 2022-2-9 15:08
 * @Version: 1.0
 * @Description:双重锁校验(线程安全)
 */
public class Singleton_05 {
    private static volatile Singleton_05 instance;

    private Singleton_05() {
    }

    public static Singleton_05 getInstance(){
        if(null != instance) return instance;
        synchronized (Singleton_05.class){
            if (null == instance){
                instance = new Singleton_05();
            }
        }
        return instance;
    }
}

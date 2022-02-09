package org.itstack.demo.design;/**
 * Project: itstack-demo-design
 * Package: org.itstack.demo.design
 * Version: 1.0
 * Created by ljy on 2022-2-9 15:10
 */

/**
 * @ClassName Singleton_07
 * @Author: ljy on 2022-2-9 15:10
 * @Version: 1.0
 * @Description:Effective Java作者推荐的枚举单例(线程安全
 */
public enum Singleton_07 {

    INSTANCE;
    public void test(){
        System.out.println("hi~");
    }

}

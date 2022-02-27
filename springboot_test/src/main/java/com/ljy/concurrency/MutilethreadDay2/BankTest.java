package com.ljy.concurrency.MutilethreadDay2;/**
 * Project: springboot_test
 * Package: com.ljy.concurrency.MutilethreadDay2
 * Version: 1.0
 * Created by ljy on 2022-2-25 9:45
 */

/**
 * @ClassName BankTest
 * @Author: ljy on 2022-2-25 9:45
 * @Version: 1.0
 * @Description:
 *
 * 使用同步机制将单例模式中的懒汉式改写线程安全的
 */
public class BankTest {
}

class Bank{

    private static Bank instance;
    private Bank(){}

    public Bank getInstance(){
    //方式一：效率稍差
        /*synchronized (Bank.class){
            if (null!=instance) return instance;
            return new Bank();
        }*/

        //方式二:效率更高
        if (instance == null){
            synchronized (Bank.class){
                if (instance == null){
                    instance = new Bank();
                }
            }
        }
        return instance;
    }
}

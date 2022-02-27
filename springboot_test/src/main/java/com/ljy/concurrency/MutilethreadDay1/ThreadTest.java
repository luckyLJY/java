package com.ljy.concurrency.MutilethreadDay1;/**
 * Project: springboot_test
 * Package: com.ljy.concurrency.Mutilethread
 * Version: 1.0
 * Created by ljy on 2022-2-22 20:50
 */

/**
 * @ClassName ThreadTest
 * @Author: ljy on 2022-2-22 20:50
 * @Version: 1.0
 * @Description:
 * 多线程的创建，方式一：继承于Thread类
 * 1. 创建一个继承于Thread类的子类
 * 2. 重写Thread类的run() --> 将此线程执行的操作声明在run()中
 * 3. 创建Thread类的子类的对象
 * 4. 通过此对象调用start()
 * <p>
 * 例子：遍历100以内的所有的偶数
 */
//1. 创建一个继承于Thread类的子类
class MyThread extends Thread{
    //2. 重写Thread类的run()
    @Override
    public void run(){
        for (int i = 0;i<100;i++){
            if (i%2 == 0){
                System.out.println(Thread.currentThread().getName()+":"+i);
            }
        }
    }
}

public class ThreadTest{
    public static void main(String[] args){
        //3. 创建Thread类的子类的对象
        MyThread t1 = new MyThread();

        //4. 通过此对象调用start()：1启动当前线程 2调用当前线程的run()
        t1.start();

        MyThread t2 = new MyThread();
        t2.start();

        //仍然在main线程中执行的
        for (int i=0;i<100;i++){
            if (i%2 ==0){
                System.out.println(Thread.currentThread().getName()+":"+i+"**main**");
            }
        }
    }
}

















package com.ljy.concurrency.MutilethreadDay2;/**
 * Project: springboot_test
 * Package: com.ljy.concurrency.MutilethreadDay2
 * Version: 1.0
 * Created by ljy on 2022-2-27 17:52
 */

/**
 * @ClassName CommunicationTest
 * @Author: ljy on 2022-2-27 17:52
 * @Version: 1.0
 * @Description:
 * 线程通信：
 * 使用两个线程打印1-100，线程1，线程2, 交替打印
 */

class Number implements Runnable{
    private int number = 1;

    @Override
    public void run() {
        while (true){
            synchronized (this) {
                //唤醒另个线程
                notify();
                if (number <= 100) {

                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    System.out.println(Thread.currentThread().getName() + ":" + number);
                    number++;

                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    break;
                }
            }
        }
    }
}
public class CommunicationTest {
    public static void main(String[] args) {
        Number number = new Number();
        Thread t1 = new Thread(number);
        Thread t2 = new Thread(number);

        t1.start();
        t2.start();
    }
}

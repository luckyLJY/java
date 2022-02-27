package com.ljy.concurrency.MutilethreadDay2;/**
 * Project: springboot_test
 * Package: com.ljy.concurrency.MutilethreadDay2
 * Version: 1.0
 * Created by ljy on 2022-2-24 17:33
 */

/**
 * @ClassName WindowTest3
 * @Author: ljy on 2022-2-24 17:33
 * @Version: 1.0
 * @Description:
 *
 * 使用同步方法付解决实现Runnable接口的线程安全问题
 *
 * 关于同步方法的总结：
 * 1.同步方法仍然涉及到同步监视器，只是不需要我们显示的声明。
 * 2.非静态的同步方法，同步监视器的是:this
 *   静态的同步方法，同步监视器是:当前类本身
 */

class Window3 implements Runnable{

    private int ticket = 100;

    @Override
    public void run(){
        while (true){
            show();
        }
    }

    private synchronized void show() {//同步监视器：this
        //synchronized (this) {
            if (ticket > 0) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + ":卖票，票号为：" + ticket);
                ticket--;
            }
        //}
    }
}
public class WindowTest3 {
    public static void main(String[] args) {
        Window3 w = new Window3();

        Thread t1 = new Thread(w);
        Thread t2 = new Thread(w);
        Thread t3 = new Thread(w);

        t1.setName("窗口1");
        t2.setName("窗口2");
        t3.setName("窗口3");

        t1.start();
        t2.start();
        t3.start();
    }
}

package com.ljy.concurrency.MutilethreadDay1;/**
 * Project: springboot_test
 * Package: com.ljy.concurrency.MutilethreadDay1
 * Version: 1.0
 * Created by ljy on 2022-2-23 21:25
 */

/**
 * @ClassName WindowTest
 * @Author: ljy on 2022-2-23 21:25
 * @Version: 1.0
 * @Description:
 * 继承Thread类的方式
 */


class Window extends Thread{


    private static int ticket = 100;
    @Override
    public void run() {

        while(true){

            if(ticket > 0){
                System.out.println(getName() + "：卖票，票号为：" + ticket);
                ticket--;
            }else{
                break;
            }

        }

    }
}


public class WindowTest {
    public static void main(String[] args) {
        Window t1 = new Window();
        Window t2 = new Window();
        Window t3 = new Window();


        t1.setName("窗口1");
        t2.setName("窗口2");
        t3.setName("窗口3");

        t1.start();
        t2.start();
        t3.start();

    }
}


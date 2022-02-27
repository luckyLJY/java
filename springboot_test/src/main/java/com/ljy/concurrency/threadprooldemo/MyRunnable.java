package com.ljy.concurrency.threadprooldemo;/**
 * Project: springboot_test
 * Package: com.ljy.concurrency.threadprool
 * Version: 1.0
 * Created by ljy on 2022-2-22 14:33
 */

import java.util.Date;

/**
 * @ClassName MyRunnable
 * @Author: ljy on 2022-2-22 14:33
 * @Version: 1.0
 * @Description:
 * Runnable接口实现来执行任务
 */
public class MyRunnable implements Runnable{

    private String command;

    public MyRunnable(String command) {
        this.command = command;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " Start. Time = " + new Date());
        processCommand();
        System.out.println(Thread.currentThread().getName() + " End. Time = " + new Date());
    }

    private void processCommand() {
        try {
            Thread.sleep(5000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    @Override
    public String toString(){
        return  this.command;
    }
}

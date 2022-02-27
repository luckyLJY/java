package com.ljy.concurrency.MutilethreadDay2;/**
 * Project: springboot_test
 * Package: com.ljy.concurrency.MutilethreadDay2
 * Version: 1.0
 * Created by ljy on 2022-2-25 10:26
 */

/**
 * @ClassName DeadLock
 * @Author: ljy on 2022-2-25 10:26
 * @Version: 1.0
 * @Description:
 * 死锁的演示
 */
class A{
    public synchronized void foo(B b){
        System.out.println("当前线程名："+Thread.currentThread().getName()
        +"进入A实例的foo方法"); //第一步
        try {
            Thread.sleep(200);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        System.out.println("当前线程名："+Thread.currentThread().getName()
        +"调用B实例的last方法");//第三步

        b.last();
    }

    public synchronized void last(){
        System.out.println("进入A类的last方法内部");
    }
}

class B{
    public synchronized void bar(A a){
        System.out.println("当前线程名："+Thread.currentThread().getName()
                +"进入B实例的foo方法"); //第二步

        		try {
			Thread.sleep(200);
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
        System.out.println("当前线程名："+Thread.currentThread().getName()
                +"调用A实例的last方法");//第四步

        a.last();
    }

    public synchronized void last(){
        System.out.println("进入A类的last方法内部");
    }
}


public class DeadLock implements Runnable{

    A a =new A();
    B b = new B();

    public void init(){
        Thread.currentThread().setName("主线程");
        //调用a对象的foo方法
        a.foo(b);
        System.out.println("进入了主线程之后");
    }
    @Override
    public void run() {
        Thread.currentThread().setName("副线程");
        //调用b对象的bar方法
        b.bar(a);
        System.out.println("进入副线程后");
    }

    public static void main(String[] args) {
        DeadLock d1 = new DeadLock();
        new Thread(d1).start();

        d1.init();

    }
}

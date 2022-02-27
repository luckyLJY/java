package com.ljy.concurrency.threadprooldemo;/**
 * Project: springboot_test
 * Package: com.ljy.concurrency.threadprooldemo
 * Version: 1.0
 * Created by ljy on 2022-2-22 14:38
 */

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName ThreadPoolExecutorDemo
 * @Author: ljy on 2022-2-22 14:38
 * @Version: 1.0
 * @Description:
 * 线程池的创建
 */
public class ThreadPoolExecutorDemo {

    private static final int CORE_POOL_SIZE = 5;
    private static final int MAX_POOL_SIZE = 10;
    private static final int QUEUE_CAPACITY = 100;
    private static final Long KEEP_ALIVE_TIME = 1L;

    /**
     * corePoolSize: 核心线程数为 5。
     * maximumPoolSize ：最大线程数
     * 10 keepAliveTime : 等待时间为 1L。
     * unit: 等待时间的单位为 TimeUnit.SECONDS。
     * workQueue：任务队列为 ArrayBlockingQueue，
     * 并且容量为 100;
     * handler:饱和策略为 CallerRunsPolicy
     * @param args
     */
    public static void main(String[] args){
        //通过ThreadPoolExecutor构造函数自定义参数创建
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                CORE_POOL_SIZE,
                MAX_POOL_SIZE,
                KEEP_ALIVE_TIME,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(QUEUE_CAPACITY),
                new ThreadPoolExecutor.CallerRunsPolicy());

        for (int i =0;i<10;i++){
            //创建WorkerThread对象，（WorkerThread类实现了Runnable 接口）
            Runnable worker = new MyRunnable("" + i);
            //执行Runnable
            executor.execute(worker);
        }
        //终止线程池
        executor.shutdown();
        while (!executor.isTerminated()){}
        System.out.println("Finished all threads");
    }
}

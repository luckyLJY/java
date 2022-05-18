package com.macro.mall.tiny.example;/**
 * Project: mall-tiny-lombok
 * Package: com.macro.mall.tiny.example
 * Version: 1.0
 * Created by ljy on 2022-5-7 10:31
 */

import lombok.*;

/**
 * @ClassName SynchronizedExample
 * @Author: ljy on 2022-5-7 10:31
 * @Version: 1.0
 * @Description:
 * @Synchronized注解同样可以实现同步访问。
 */
@Data
public class SynchronizedExample {

    @NonNull
    private Integer count;

    @Synchronized
    @SneakyThrows
    public void reducecount(Integer id){
        if (count > 0){
            Thread.sleep(500);
            count --;
            System.out.println(String.format("thread-%d count:%d",id,count));
        }
    }

    public static void main(String[] args) {
        SynchronizedExample example = new SynchronizedExample(20);
        new ReduceThread(1,example).start();
        new ReduceThread(2,example).start();
        new ReduceThread(3,example).start();
    }

    @RequiredArgsConstructor
    static class ReduceThread extends Thread{
        @NonNull
        private Integer id;
        @NonNull
        private SynchronizedExample example;

        @Override
        public void run() {
            while (example.getCount()>0){
                example.reducecount(id);
            }
        }
    }

}

package com.ljy.jvm.chapter5;/**
 * Project: JVM
 * Package: com.ljy.jvm.chapter5
 * Version: 1.0
 * Created by ljy on 2022-6-29 21:05
 */

/**
 * @ClassName:StackErrorTest
 * @Author: ljy on 2022-6-29 21:05
 * @Version: 1.0
 * @Description:
 *
 * 演示栈中的异常：StackOverflowError
 * 默认情况下：count:11420
 * 设置栈的大小：-Xss256k:count :2465
 */
public class StackErrorTest {

    private static int count= 1;

    public static void main(String[] args) {
        System.out.println(count);
        count++;
        main(args);
    }
}

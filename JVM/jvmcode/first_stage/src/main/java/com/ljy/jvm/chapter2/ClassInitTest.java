package com.ljy.jvm.chapter2;/**
 * Project: JVM
 * Package: com.ljy.jvm.chapter1.chapter2
 * Version: 1.0
 * Created by ljy on 2022-6-20 22:11
 */

/**
 * @ClassName ClassInitTest
 * @Author: ljy on 2022-6-20 22:11
 * @Version: 1.0
 * @Description:
 */
public class ClassInitTest {
    private static int num = 1;

    static {
        num =2;
        number =20;
        System.out.println(num);
        //System.out.println(number);//非法的前向引用
    }
    private static int number =10;

    public static void main(String[] args) {
        System.out.println(ClassInitTest.num);
        System.out.println(ClassInitTest.number);
    }
}

package com.ljy.jvm.chapter2;/**
 * Project: JVM
 * Package: com.ljy.jvm.chapter2
 * Version: 1.0
 * Created by ljy on 2022-6-20 22:28
 */

/**
 * @ClassName
 * @Author: ljy on 2022-6-20 22:28
 * @Version: 1.0
 * @Description:
 */
public class ClinitTest1 {

    static class Father{
        public static int A =1;
        static {
            A=2;
        }
    }

    static  class  Son extends Father{
        public static int B=A;
    }

    public static void main(String[] args) {
        //加载Father类，其次加载son
        System.out.println(Son.B);
    }
}

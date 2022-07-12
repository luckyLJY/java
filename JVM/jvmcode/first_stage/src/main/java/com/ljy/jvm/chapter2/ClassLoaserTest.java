package com.ljy.jvm.chapter2;/**
 * Project: JVM
 * Package: com.ljy.jvm.chapter2
 * Version: 1.0
 * Created by ljy on 2022-6-22 21:33
 */

/**
 * @ClassName
 * @Author: ljy on 2022-6-22 21:33
 * @Version: 1.0
 * @Description:
 */
public class ClassLoaserTest {
    public static void main(String[] args) {

        //获取系统类加载器
        ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
        System.out.println(systemClassLoader);//sun.misc.Launcher$AppClassLoader@18b4aac2

        //获取其上层:扩展加载器
        ClassLoader extClassLoader = systemClassLoader.getParent();
        System.out.println(extClassLoader);//sun.misc.Launcher$ExtClassLoader@135fbaa4

        //获取其上层:获取不到引导类加载器
        ClassLoader bootstrapClassLoader = extClassLoader.getParent();
        System.out.println(bootstrapClassLoader);//null

        //对于用户自定义来说：默认使用系统类加载器进行加载
        ClassLoader classLoader = ClassLoaserTest.class.getClassLoader();
        System.out.println(classLoader);//sun.misc.Launcher$AppClassLoader@18b4aac2

        //String类使用引导类加载器进行加载，Java核心类库
        ClassLoader classLoader1 = String.class.getClassLoader();
        System.out.println(classLoader1);//null


    }
}

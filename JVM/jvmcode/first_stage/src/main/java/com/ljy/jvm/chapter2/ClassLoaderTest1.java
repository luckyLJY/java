package com.ljy.jvm.chapter2;/**
 * Project: JVM
 * Package: com.ljy.jvm.chapter2
 * Version: 1.0
 * Created by ljy on 2022-6-23 21:25
 */

import sun.misc.Launcher;
import sun.security.ec.CurveDB;

import java.net.URL;
import java.security.Provider;

/**
 * @ClassName ClassLoaderTest1
 * @Author: ljy on 2022-6-23 21:25
 * @Version: 1.0
 * @Description:
 */
public class ClassLoaderTest1 {
    public static void main(String[] args) {
        System.out.println("**********启动类加载器*************");
        URL[] urLs = Launcher.getBootstrapClassPath().getURLs();
        for (URL urL : urLs) {
            System.out.println(urL.toExternalForm());
        }

        //从上面的路径中随意选择一个类，引导类加载器
        ClassLoader classLoader = Provider.class.getClassLoader();
        System.out.println(classLoader);

        System.out.println("*********扩展类加载器*********");
        String extDirs = System.getProperty("java.ext.dirs");
        for (String path : extDirs.split(";")) {
            System.out.println(path);
        }

        //上面随便选择一个类的类加载器：
        ClassLoader classLoader1 = CurveDB.class.getClassLoader();
        System.out.println(classLoader1);//sun.misc.Launcher$ExtClassLoader@511d50c0

    }
}

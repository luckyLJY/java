package com.ljy.jvm.chapter2;/**
 * Project: JVM
 * Package: com.ljy.jvm.chapter2
 * Version: 1.0
 * Created by ljy on 2022-6-23 21:53
 */

import java.io.FileNotFoundException;

/**
 * @ClassName CustomClassLoader
 * @Author: ljy on 2022-6-23 21:53
 * @Version: 1.0
 * @Description:
 */
public class CustomClassLoader extends ClassLoader {

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        try {
            byte[] result = getClassFromCustomPath(name);
            if (result == null){
                throw new FileNotFoundException();
            }else {
                return defineClass(name,result,0,result.length);
            }
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
       throw new ClassNotFoundException(name);
    }

    private byte[] getClassFromCustomPath(String name){
        //如果指定路径的字节文件进行加密，则需要在此方法中进行解密
        return null;
    }

    public static void main(String[] args) {
        CustomClassLoader customClassLoader = new CustomClassLoader();
        try {
            Class<?> clazz = Class.forName("One", true, customClassLoader);
            Object obj = clazz.newInstance();
            System.out.println(obj);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

package org.itstack.demo.design.util;/**
 * Project: itstack-demo-design
 * Package: org.itstack.demo.design.util
 * Version: 1.0
 * Created by ljy on 2022-1-10 14:19
 */

import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

/**
 * @ClassName ClassLoaderUtils
 * @Author: ljy on 2022-1-10 14:19
 * @Version: 1.0
 * @Description:
 * 类和方法加载工具类
 */
public class ClassLoaderUtils {

    private static Set<Class> primitiveSet = new HashSet<Class>();

    static {
        primitiveSet.add(Integer.class);
        primitiveSet.add(Long.class);
        primitiveSet.add(Float.class);
        primitiveSet.add(Byte.class);
        primitiveSet.add(Short.class);
        primitiveSet.add(Double.class);
        primitiveSet.add(Character.class);
        primitiveSet.add(Boolean.class);
    }

    /**
     * 得到当前ClassLoader
     * @return ClassLoader
     */
    public static ClassLoader getCurrentClassLoader(){
        ClassLoader c1 = Thread.currentThread().getContextClassLoader();
        if (c1 == null){
            c1 = ClassLoaderUtils.class.getClassLoader();
        }
        return c1 == null ? ClassLoader.getSystemClassLoader():c1;
    }

    /**
     * 得到当前的ClassLoader
     * @param clazz
     * @return ClassLoader
     */
    public static ClassLoader getClassLoader(Class<?> clazz){
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        if (loader != null){
            return loader;
        }
        if (clazz !=null){
            loader = clazz.getClassLoader();
            if (loader!=null){
                return loader;
            }
            return clazz.getClassLoader();
        }
        return ClassLoader.getSystemClassLoader();
    }

    /**
     * 根据类名加载Class
     *
     * @param className 类名
     * @return Class
     * @throws ClassNotFoundException 找不到类
     */
    public static Class forName(String className)throws ClassNotFoundException{
        return forName(className,true);
    }

    /**
     * 根据类名加载Class
     *
     * @param className 类名
     * @param initialize 是否初始化
     * @return class
     * @throws ClassNotFoundException 找不到类
     */
    public static Class forName(String className,boolean initialize) throws ClassNotFoundException{
        return Class.forName(className,initialize,getCurrentClassLoader());
    }

    /**
     * 根据类名加载Class
     *
     * @param className 类名
     * @param c1 Classloader
     * @return Class
     * @throws ClassNotFoundException  找不到类
     */
    public static Class forName(String className, ClassLoader c1)throws ClassNotFoundException{
        return Class.forName(className,true,c1);
    }

    public static <T> T newInstance(Class<T> clazz) throws Exception{
        if (primitiveSet.contains(clazz)){
            return null;
        }
        if (clazz.isMemberClass() && !Modifier.isStatic(clazz.getModifiers())){

        }
    }
}

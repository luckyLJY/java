package org.itstack.demo.design.util;/**
 * Project: itstack-demo-design
 * Package: org.itstack.demo.design.util
 * Version: 1.0
 * Created by ljy on 2022-1-10 14:19
 */

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.TimeUnit;

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
        /**
         * TODO:
         *  static Thread	currentThread(): 返回对当前正在执行的线程对象的引用。
         *  ClassLoader	getContextClassLoader():返回此Thread的上下文ClassLoader。
         *  static ClassLoader	getSystemClassLoader():返回用于委派的系统类加载器。
         */
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
            //TODO: ClassLoader	getClassLoader():返回类的类加载器。
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

    /**
     * 实例化一个对象(只检测默认构造函数，其它不管)
     *
     * @param clazz 对象类
     * @param <T> 对象具体类
     * @return 对象实例
     * @throws Exception 没有找到方法，或者无法处理，或者初始化方法异常等
     */
    public static <T> T newInstance(Class<T> clazz) throws Exception{
        if (primitiveSet.contains(clazz)){
            return null;
        }
        /**
         * TODO:
         *  int getModifiers():返回由该对象表示的可执行文件的Java语言modifiers。
         *  static boolean	isStatic(int mod):如果整数参数包含 static修饰符，则返回 true ， false false。
         *  defaultConstructor.isAccessible() 获取此对象的可访问标志的值。返回：对象的可访问标志的值
         */
        if (clazz.isMemberClass() && !Modifier.isStatic(clazz.getModifiers())){
            Constructor constructorList[] = clazz.getDeclaredConstructors();
            Constructor defaultConstructor = null;
            for (Constructor con : constructorList) {
                if (con.getParameterTypes().length == 1){
                    defaultConstructor =con;
                    break;
                }
            }
            if (defaultConstructor != null){
                if (defaultConstructor.isAccessible()){
                    return (T) defaultConstructor.newInstance(new Object[]{null});
                }else {
                    try {
                        defaultConstructor.setAccessible(true);
                        return (T) defaultConstructor.newInstance(new Object[]{null});
                    }finally {
                        defaultConstructor.setAccessible(false);
                    }
                }
            }else {
                throw new Exception("The " + clazz.getCanonicalName() + " has no default constructor!");
            }
        }
        try {
            return clazz.newInstance();
        }catch (Exception e){
          Constructor<T> constructor =  clazz.getDeclaredConstructor();
          if (constructor.isAccessible()){
              throw new Exception("The " + clazz.getCanonicalName() + " has no default constructor!", e);
          }else{
              try {
                  constructor.setAccessible(true);
                  return constructor.newInstance();
              }finally {
                  constructor.setAccessible(false);
              }
          }
        }
    }

    public static Class<?>[] getClazzByArgs(Object[] args){
        Class<?>[] parameterTypes = new Class[args.length];
        for (int i=0;i<args.length;i++){
            if (args[i] instanceof ArrayList){
                parameterTypes[i] = List.class;
                continue;
            }

            if (args[i] instanceof HashMap) {
                parameterTypes[i] = Map.class;
                continue;
            }
            if (args[i] instanceof Long){
                parameterTypes[i] = long.class;
                continue;
            }
            if (args[i] instanceof Double){
                parameterTypes[i] = double.class;
                continue;
            }
            if (args[i] instanceof TimeUnit){
                parameterTypes[i] = TimeUnit.class;
                continue;
            }
            parameterTypes[i] = args[i].getClass();
        }
        return parameterTypes;
    }

    public Method getMethod(Class<?> classType,String methodName,Class<?>...parameterTypes)throws NoSuchMethodException {
        return classType.getMethod(methodName, parameterTypes);
    }
}

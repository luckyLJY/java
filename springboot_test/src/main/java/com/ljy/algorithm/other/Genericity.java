package com.ljy.algorithm.other;/**
 * Project: springboot_test
 * Package: com.ljy.algorithm
 * Version: 1.0
 * Created by ljy on 2022-1-13 15:22
 */

/**
 * @ClassName Genericity
 * @Author: ljy on 2022-1-13 15:22
 * @Version: 1.0
 * @Description
 */
public class Genericity<T> {
    public static void main(String[] args) {
        Genericity<String> test = new Genericity<>("test1");
        System.out.println(test.getGenericityName());
        System.out.println(test.getClass());
       // Class<?> aClass = Class.forName(test.getGenericityName());
        Genericity<Double> test1 = new Genericity<>(new Double(123));
        System.out.println(test1.getGenericityName());
    }
    private T data;
    public Genericity(T data) {
        this.data = data;
    }
    public T getData() {
        return this.data;
    }
    public String getGenericityName() {
        return data.getClass().getName();
    }
}

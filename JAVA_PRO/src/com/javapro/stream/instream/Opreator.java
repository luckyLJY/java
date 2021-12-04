package com.javapro.stream.instream;/**
 * Project: JAVA_PRO
 * Package: com.javapro.stream.instream
 * Version: 1.0
 * Created by ljy on 2021-12-3 11:05
 */

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.function.IntPredicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @ClassName Opereater
 * @Author: ljy on 2021-12-3 11:05
 * @Version: 1.0
 * @Description: IntStream
 */
public class Opreator {
    public static void main(String[] args) {
        //int类型求平均值
        //createControler();
        //int类型--count:个数，sum:总值，min:最小值，max:最大值，avr:平均值
        //sumAge();
        //从0到10的stream的平均值
        //avgIntStream();
        //TODO:生成IntStream
        //createInStream();
        //TODO: 生成随机数
        //createRandomInt();
        //TODO:计算大于3的第一个偶数的平方
        //com();
        //TODO:整数数组翻转
    /*    Integer[] numbers = {1,2,3};
        reverIntArray(numbers);
        System.out.println(Arrays.asList(numbers));*/

        //TODO:检查整数书否为素数
        System.out.println(isPrime(11));
    }

    /**
     * 创建带有构造器的IntStream
     * int数据求平均值
     */
    public static void createControler(){
        IntStream
                .builder()
                .add(2)
                .add(4)
                .add(9)
                .build()
                .average()
                .ifPresent(System.out::println);
    }

    /**
     * 从收集器创建IntSummaryStatics
     * 计算person的年龄综合
     */
    public static void sumAge(){
        List<Person> persons =
                Arrays.asList(
                        new Person("Max",18),
                        new Person("Peter",23),
                        new Person("David",12));

        IntSummaryStatistics ageSummarg = persons.stream().collect(Collectors.summarizingInt(p -> p.age));

        System.out.println(ageSummarg);
    }

    /**
     * 创建从0到10的int流，并获取得平均值
     */
    public static void avgIntStream(){
        IntStream
                .range(0,10)
                .average()
                .ifPresent(System.out::println);
    }

    /**
     * TODO:从IntStream生成常量值
     */
    public static void createStaConValue(){

    }

    /**
     * TODO: 从IntSupplier为IntStream生成int
     */
    public static void createIntStreamFromIntSupplier(){

    }

    /**
     * TODO:生成Int流
     */
    public static void createInStream(){
        //前闭后开
        IntStream.rangeClosed(1,10).forEach(num -> System.out.println(num));
        System.out.println("============");
        IntStream.range(1,10).forEach(num -> System.out.println(num));
    }

    /**
     * TODO：生成随机整数
     */
    public static void createRandomInt(){
        SecureRandom secureRandom = new SecureRandom(new byte[]{1, 3, 3, 7});
        int[] randoms = IntStream.generate(secureRandom::nextInt)
                .filter(n -> n > 0)
                .limit(10)
                .toArray();
        System.out.println(Arrays.toString(randoms));

        int[] nums = IntStream.iterate(1, n -> n * 2)
                .limit(11)
                .toArray();
        System.out.println(Arrays.toString(nums));

    }

    /**
     * TODO:从列表中获取大于3的第一个偶数的平方
     */
    public static void com(){
        List<Integer> nums = Arrays.asList(1, 2, 3, 4, 5);

        System.out.println(
                nums.stream()
                .filter(i-> i>2)
                .filter(i -> i%2==0)
                .mapToInt(i -> i*i)
                .findFirst()
                .getAsInt()
        );
    }

    /**
     * TODO:从对象流映射到IntStream
     */
    public static void objectMapp(){

    }

    /**
     * TODO:整数数组翻转
     */
    public static void reverIntArray(Integer[] numbers){
        Arrays.sort(numbers,(Integer i1, Integer i2) -> {return (i2 -i1);});
    }

    /**
     * TODO:使用lambda检查整数是否是素数
     */
    public static boolean isPrime(int number){
        IntPredicate isDivisible = index -> number % index ==0;
        return number > 1 && IntStream.range(2, number - 1).noneMatch(isDivisible);
    }
}
class Person{
    String name;
    int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                '}';
    }
}
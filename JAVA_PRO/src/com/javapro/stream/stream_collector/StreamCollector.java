package com.javapro.stream.stream_collector;/**
 * Project: JAVA_PRO
 * Package: com.javapro.stream.stream_averge
 * Version: 1.0
 * Created by ljy on 2021-12-6 16:22
 */

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @ClassName StreamAvg
 * @Author: ljy on 2021-12-6 16:22
 * @Version: 1.0
 * @Description：
 * TODO:
 *      Collector操作
 */
public class StreamCollector {
    public static void main(String[] args) {
        //TODO:TODO:将list中的数据按长度统计返回为Map
        //comListLen2Map();
        //TODO:供应商，储能器，组合器，整合器
        //use();
        //TODO:group按人员年龄列出的人员列表。
        //groupbyAge();

        //TODO:使用收集器创建从整数到字符串的人物列表的映射
        //figMapper();


    }

    /**
     * TODO:将list中的数据按长度统计返回为Map
     */
    public static void comListLen2Map(){
        LinkedList<String> strings = new LinkedList<>();
        strings.add("a");
        strings.add("bb");
        strings.add("BB");
        strings.add("ccc");
        strings.add("ABCD");

        Map<Integer, List<String>> collect = strings.parallelStream().collect(Collectors.groupingBy(String::length));
        System.out.println(collect);
    }

    /**
     * TODO:
     * 供应商，蓄能器，组合器和整理器创建收集器的使用
     */
    public static void use(){
        List<Person> persons =
                Arrays.asList(
                        new Person("Max", 18),
                        new Person("Peter", 23),
                        new Person("Pamela", 23),
                        new Person("David", 12));

        Collector<Person, StringJoiner, String> stringJoinerSupplier = Collector.of(
                () -> new StringJoiner(" | "), //supplier供应商
                (j, p) -> j.add(p.name.toUpperCase()),  // accumulator 蓄能器
                (j1, j2) -> j1.merge(j2),               // combiner组合器
                StringJoiner::toString);// finisher整理器

        String collect = persons.stream().collect(stringJoinerSupplier);
        System.out.println(collect);
    }

    /**
     * TODO:group按人员年龄列出的人员列表。
     */
    public static void groupbyAge(){
        List<Person> persons =
                Arrays.asList(
                        new Person("Max", 18),
                        new Person("Peter", 23),
                        new Person("Pamela", 23),
                        new Person("David", 12));
        Map<Integer, List<Person>> collect = persons.stream().collect(Collectors.groupingBy(p -> p.age));

        collect.forEach((age, p) -> System.out.format("age %s: %s\n", age, p));
    }

    //TODO:使用收集器创建从整数到字符串的人物列表的映射
    public static void figMapper(){
        List<Person> persons =
                Arrays.asList(
                        new Person("Max", 18),
                        new Person("Peter", 23),
                        new Person("Pamela", 23),
                        new Person("David", 12));

        Map<Integer, String> map = persons.stream().collect(Collectors.toMap(
                p -> p.age,
                p -> p.name,
                (name1, name2) -> name1 + ";" + name2));

        System.out.println(map);
    }


}

class Person {
    String name;
    int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return name;
    }
}

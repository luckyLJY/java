package com.javapro;/**
 * Project: JAVA_PRO
 * Package: com.javapro
 * Version: 1.0
 * Created by ljy on 2021-12-6 11:51
 */



import java.math.BigDecimal;
import java.util.*;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @ClassName Other
 * @Author: ljy on 2021-12-6 11:51
 * @Version: 1.0
 * @Description
 */
public class Other {
    public static void main(String[] args) {
        /*BigDecimal a =new BigDecimal("1");
        BigDecimal b =new BigDecimal("2");
        List<BigDecimal> names = Arrays.asList(a,b);
        names.sort(Collections.reverseOrder());
        System.out.println(names);*/
        Map<String ,String> inputMap = new HashMap<>();
        inputMap.put("a","0");
        inputMap.put("b","");

        List<Map<String,String>> inputList = new ArrayList<Map<String,String>>();
        Map<String,String> map1 = new HashMap<>();
        map1.put("a","0");
        map1.put("b","1");
        Map<String,String> map2 = new HashMap<>();
        map2.put("a","1");
        map2.put("b","1");

        inputList.add(map1);
        inputList.add(map2);

        List<Map<String, String>> c = inputList.stream().filter(
                new Predicate<Map<String, String>>() {
                    //""  ==

                    @Override
                    public boolean test(Map<String, String> stringStringMap) {
                        boolean rest = test1(inputMap.get("a"),stringStringMap);
                        boolean test1 = test1(inputMap.get("b"),stringStringMap);
                        if (rest && test1){
                            return true;
                        }
                        return false;
                    }
                }
        ).collect(Collectors.toList());

        System.out.println(c);

    }

    public static boolean test1(String str, Map<String,String> map){
        boolean result =false;
        if ("".equals(str))
        {
            result = true;
        }
        if (str.equals(map.get("a"))){
            result =true;
        }

        if (str.equals(map.get("b"))){
            result =true;
        }

        return result;
    }
    public static void test(){

        String a ="";
        String b="1";

        List<Map<String,String>> inputList = new ArrayList<Map<String,String>>();
        Map<String,String> map1 = new HashMap<>();
        map1.put("a","1");
        Map<String,String> map2 = new HashMap<>();
        map2.put("b","1");

        inputList.add(map1);
        inputList.add(map2);
        List<Map<String, String>> collect = inputList.stream().filter(p -> p.get("a").equals(a)).filter(p -> p.get("b").equals(b)).collect(Collectors.toList());
        System.out.println(collect);
    }
}

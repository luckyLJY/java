package com.javapro;/**
 * Project: JAVA_PRO
 * Package: com.javapro
 * Version: 1.0
 * Created by ljy on 2021-12-6 11:51
 */

import java.math.BigDecimal;
import java.util.*;

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

        List<Map<String,String>> inputList = new ArrayList<Map<String,String>>();
        Map<String,String> map1 = new HashMap<>();
        map1.put("a","1");
        Map<String,String> map2 = new HashMap<>();
        map2.put("b"," ");

        inputList.add(map1);
        inputList.add(map2);

        System.out.printf("List", inputList);
    }
}

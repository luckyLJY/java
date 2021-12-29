package com.ljy.algorithm.simple;/**
 * Project: springboot_test
 * Package: com.ljy.algorithm
 * Version: 1.0
 * Created by ljy on 2021-12-27 11:01
 */

import java.util.HashMap;
import java.util.Map;

import static javax.swing.UIManager.put;

/**
 * @ClassName Roman_Num2_Int
 * @Author: ljy on 2021-12-27 11:01
 * @Version: 1.0
 * @Description:罗马数字转整数
 * 方法：
 *  判断第i个字符与第i+1个字符所对应的值
 *  value(i) < value(i+1) result += -value(i)
 *  else result += value(i)
 */
public class Roman_Num2_Int {
    public static void main(String[] args) {
        String s = "IV";
        int result = solution(s);
        System.out.println(result);
    }
    public static int solution(String s){
        Map<Character,Integer> symbolValues = new HashMap<Character,Integer>(){{
            put('I', 1);
            put('V', 5);
            put('X', 10);
            put('L', 50);
            put('C', 100);
            put('D', 500);
            put('M', 1000);
        }};

        int ans = 0;
        int n  = s.length();
        for (int i = 0; i < n; i++) {
            int value = symbolValues.get(s.charAt(i));
            if (i < n-1 && value < symbolValues.get(s.charAt(i+1))){
                ans -= value;
            }else {
                ans += value;
            }
        }
        return  ans;
    }
}

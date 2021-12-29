package com.ljy.algorithm.simple;/**
 * Project: springboot_test
 * Package: com.ljy.algorithm
 * Version: 1.0
 * Created by ljy on 2021-12-27 15:01
 */

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import static javax.swing.UIManager.put;

/**
 * @ClassName Effective_Brackets
 * @Author: ljy on 2021-12-27 15:01
 * @Version: 1.0
 * @Description:有效括号
 * 1 <= s.length <= 104
 * s 仅由括号 '()[]{}' 组成
 * 需求：
 * 左括号必须用相同类型的右括号闭合。
 * 左括号必须以正确的顺序闭合。
 * 方法：
 * 1.将有效括号的一对结束作为键，开始作为值存储在map中
 * 2.遍历字符串存入栈中，遇到结束字符弹栈，否则false
 */
public class Effective_Brackets {
    public static void main(String[] args) {
        String s = "()[]{}";
        boolean result = solution(s);
        System.out.println(result);
    }

    public static boolean solution(String str){
        boolean res = false;
        int n = str.length();
        if (n % 2 == 1){
            return res;
        }

        Map<Character, Character> pairs = new HashMap<Character, Character>() {{
            put(')', '(');
            put(']', '[');
            put('}', '{');
        }};
        Deque<Character> stack = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            char ch = str.charAt(i);
            if (pairs.containsKey(ch)){
                if (stack.isEmpty() || stack.peek()!=pairs.get(ch)){
                    return res;
                }
                stack.pop();
            }else {
                stack.push(ch);
            }
        }
        res = stack.isEmpty();
        return res;
    }
}

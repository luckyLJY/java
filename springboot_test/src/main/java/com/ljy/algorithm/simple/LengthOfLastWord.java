package com.ljy.algorithm.simple;/**
 * Project: springboot_test
 * Package: com.ljy.algorithm.simple
 * Version: 1.0
 * Created by ljy on 2022-1-4 14:32
 */

/**
 * @ClassName LengthOfLastWord
 * @Author: ljy on 2022-1-4 14:32
 * @Version: 1.0
 * @Description:
 * 最后一个单次的长度
 */
public class LengthOfLastWord {
    public static void main(String[] args) {
        String s = "Hello word! ";
        int result = solution(s);
        System.out.println("最后一个单词长度：" + result);
    }

    public static int solution(String s){
        int index = s.length()-1;
        while (s.charAt(index)== ' '){
            index --;
        }

        int len = 0;
        while (index >= 0 && s.charAt(index)!=' '){
            len ++;
            index --;
        }
        return len;
    }
}

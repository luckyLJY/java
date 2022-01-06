package com.ljy.algorithm.simple;/**
 * Project: springboot_test
 * Package: com.ljy.algorithm.simple
 * Version: 1.0
 * Created by ljy on 2022-1-6 15:13
 */

/**
 * @ClassName AddBinary
 * @Author: ljy on 2022-1-6 15:13
 * @Version: 1.0
 * @Description：
 * 二进制相加
 */
public class AddBinary {
    public static void main(String[] args) {
        String result = solution1("11","1");
        System.out.println(result);
    }

    /**
     * 使用现有API
     * @param a
     * @param b
     * @return
     */
    public static String solution(String a,String b){
        return Integer.toBinaryString(
                Integer.parseInt(a,2)+Integer.parseInt(b,2)
        );
    }

    /**
     * 当目前未相加为2时，给值为0
     * @param a
     * @param b
     * @return
     */
    public static String solution1(String a,String b){
        StringBuffer ans = new StringBuffer();

        int n = Math.max(a.length(),b.length()),temp=0;
        for (int i=0;i<n;i++){

            temp += i < a.length() ? (a.charAt(a.length()-1-i)):0;
            temp += i < b.length() ? (b.charAt(b.length()-1-i)):0;

            ans.append((char) (temp%2+'0'));
            temp /=2;
        }

        if (temp>0){
            ans.append('1');
        }
        ans.reverse();

        return ans.toString();

    }

}

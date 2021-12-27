package com.ljy.algorithm;/**
 * Project: springboot_test
 * Package: com.ljy.algorithm
 * Version: 1.0
 * Created by ljy on 2021-12-27 10:15
 */

/**
 * @ClassName Palindrome
 * @Author: ljy on 2021-12-27 10:15
 * @Version: 1.0
 * @Description:回文数
 * 方案：
 *  示例 1：
 *
 * 输入：x = 121
 * 输出：true
 * 示例 2：
 *
 * 输入：x = -121
 * 输出：false
 * 解释：从左向右读, 为 -121 。 从右向左读, 为 121- 。因此它不是一个回文数。
 * 示例 3：
 *
 * 输入：x = 10
 * 输出：false
 * 解释：从右向左读, 为 01 。因此它不是一个回文数。
 * 示例 4：
 *
 * 输入：x = -101
 * 输出：false
 */
public class Palindrome {
    public static void main(String[] args) {
        boolean result = solution(121);
        System.out.println( result);
    }

    public static boolean solution(int x){
        if (x < 0 || (x % 10 == 0 && x != 0)) {
            return false;
        }

        int reverNumber = 0;
        while (x > reverNumber){
            reverNumber = reverNumber * 10 + x % 10;
            x /= 10;
        }

        return x == reverNumber || x == reverNumber / 10;
    }
}

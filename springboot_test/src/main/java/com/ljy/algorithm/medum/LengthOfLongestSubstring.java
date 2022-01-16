package com.ljy.algorithm.medum;/**
 * Project: springboot_test
 * Package: com.ljy.algorithm.medum
 * Version: 1.0
 * Created by ljy on 2022-1-11 15:26
 */

import java.util.HashSet;
import java.util.Set;

/**
 * @ClassName LengthOfLongestSubstring
 * @Author: ljy on 2022-1-11 15:26
 * @Version: 1.0
 * @Description:
 *
示例1:
输入:s="abcabcbb"
输出:3
解释:因为无重复字符的最长子串是"abc"，所以其长度为3。

示例2:
输入:s="bbbbb"
输出:1
解释:因为无重复字符的最长子串是"b"，所以其长度为1。

示例3:
输入:s="pwwkew"
输出:3
解释:因为无重复字符的最长子串是"wke"，所以其长度为3。
请注意，你的答案必须是子串的长度，"pwke"是一个子序列，不是子串。

示例4:
输入:s=""
输出:0


提示：
0<=s.length<=5*104
s由英文字母、数字、符号和空格组成
 *
 */
public class LengthOfLongestSubstring {
    public static void main(String[] args) {
        String s ="abcabcbb";
        int result = solution(s);
        System.out.println(result);
    }

    public static int solution(String s){
        // 哈希集合，记录每个字符是否出现过
        Set<Character> occ = new HashSet<Character>();
        int n = s.length();
        // 右指针，初始值为 -1，相当于我们在字符串的左边界的左侧，还没有开始移动
        int rk = -1, ans = 0;
        for (int i = 0; i < n; ++i) {
            if (i != 0) {
                // 左指针向右移动一格，移除一个字符
                occ.remove(s.charAt(i - 1));
            }
            while (rk + 1 < n && !occ.contains(s.charAt(rk + 1))) {
                // 不断地移动右指针
                occ.add(s.charAt(rk + 1));
                ++rk;
            }
            // 第 i 到 rk 个字符是一个极长的无重复字符子串
            ans = Math.max(ans, rk - i + 1);
        }
        return ans;

    }
}

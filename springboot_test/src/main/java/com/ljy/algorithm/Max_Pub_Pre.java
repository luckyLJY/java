package com.ljy.algorithm;/**
 * Project: springboot_test
 * Package: com.ljy.algorithm
 * Version: 1.0
 * Created by ljy on 2021-12-27 11:15
 */

/**
 * @ClassName Max_Pub_Pre
 * @Author: ljy on 2021-12-27 11:15
 * @Version: 1.0
 * @Description:最长公共前缀
 * 需求：
 * 示例 1：
 * 输入：strs = ["flower","flow","flight"]
 * 输出："fl"
 *
 * 示例 2：
 * 输入：strs = ["dog","racecar","car"]
 * 输出：""
 * 解释：输入不存在公共前缀。
 * 1 <= strs.length <= 200
 * 0 <= strs[i].length <= 200
 * strs[i] 仅由小写英文字母组成
 * 方法：
 *  1.找到strs[i].length 最小的
 *  2.那最小的遍历整个strs
 * 优化方法：
 *  分治法：
 *
 *
 */
public class Max_Pub_Pre {
    public static void main(String[] args) {
       String[] strs = new String[]{"dog","racecar","car"};
       String result =solution(strs);
        System.out.println( result);
    }
    public static String solution(String[] strs){
        String res = "";
        if (strs == null || strs.length == 0){
            res = "";
        }else {
             res = longestCommonPrefix(strs, 0, strs.length-1);
        }
        return res;
    }

    private static String longestCommonPrefix(String[] strs, int start, int end) {
        if (start == end){
            return strs[start];
        }else {
            int mid = (end - start)/2 + start;
            String lcpLeft = longestCommonPrefix(strs,start,mid);
            String lcpRight = longestCommonPrefix(strs,mid+1,end);
            return commonPrefix(lcpLeft,lcpRight);
        }
    }

    private static String commonPrefix(String lcpLeft, String lcpRight) {
        int minLength = Math.min(lcpLeft.length(),lcpRight.length());;
        for (int i = 0; i < minLength; i++) {
            if (lcpLeft.charAt(i) != lcpRight.charAt(i)){
                return lcpLeft.substring(0,i);
            }
        }
        return lcpLeft.substring(0,minLength);
    }
}

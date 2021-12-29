package com.ljy.algorithm.simple;/**
 * Project: springboot_test
 * Package: com.ljy.algorithm
 * Version: 1.0
 * Created by ljy on 2021-12-29 11:18
 */

/**
 * @ClassName ImpStrStr
 * @Author: ljy on 2021-12-29 11:18
 * @Version: 1.0
 * @Description:实现 strStr() 函数。
 * 示例 1：
 * 输入：haystack = "hello", needle = "ll"
 * 输出：2
 *
 * 示例 2：
 * 输入：haystack = "aaaaa", needle = "bba"
 * 输出：-1
 *
 * 示例 3：
 * 输入：haystack = "", needle = ""
 * 输出：0
 *
 * 提示：
 * 0 <= haystack.length, needle.length <= 5 * 104
 * haystack 和 needle 仅由小写英文字符组成
 *
 */
public class ImpStrStr {
    public static void main(String[] args) {
        String haystack = "aabdaabaaf";
        String needle = "aabaaf";
        int result = solution(haystack,needle);
        System.out.println(result);
    }

    //aabaaf
    public static int solution(String haystack,String needle){
        int n = haystack.length(),m = needle.length();
        if (m == 0){
            return 0;
        }
        int[] pi =new int[m];
        for (int i=1,j=0;i<m;i++){
            //j>0
            /**
             * i=2
             * j=1
             */
            //i=2,j=1 |j=3,i=5,j=pi[3-1]=0
            while (j>0 && needle.charAt(i)!=needle.charAt(j)){
                //pi[j-1|1-1]=0,j=0
                j = pi[j-1];
            }
            //i=1,j=0| | i=3,j=0,j=1 |i=4,j=1,j=2
            if (needle.charAt(i) == needle.charAt(j)){
                j++;
            }
            //pi[1] = 1 | pi[2]=0 |p[3]=1 |p[4]=2|
            pi[i] = j;
        }
        for (int i = 0,j=0;i<n;i++){
            while (j>0 && haystack.charAt(i)!= needle.charAt(j)){
                j = pi[j-1];
            }
            if (haystack.charAt(i) == needle.charAt(j)){
                j++;
            }
            if (j == m){
                return i-m+1;
            }
        }
        return -1;
    }
}

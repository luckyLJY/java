package com.ljy.algorithm.simple;/**
 * Project: springboot_test
 * Package: com.ljy.algorithm
 * Version: 1.0
 * Created by ljy on 2021-12-28 11:05
 */

/**
 * @ClassName Remove_Ele
 * @Author: ljy on 2021-12-28 11:05
 * @Version: 1.0
 * @Description:移除元素
 * 思路：
 *  采用双指针
 */
public class Remove_Ele {
    public static void main(String[] args) {
       int[] nums =  new int []{3,2,2,3};
       int val = 3;
       int result = solution(nums,val);
        System.out.println("数量:"+result);
        for (int i = 0; i < result; i++) {
            System.out.println(nums[i]);
        }
    }

    public static int solution(int[] nums,int val){
        int left = 0;
        int right = nums.length;
        while (left < right){
            if (nums[left] == val){
                nums[left] = nums[right - 1];
                right --;
            }else {
                left ++;
            }
        }
        return left;
    }
}

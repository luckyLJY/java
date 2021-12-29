package com.ljy.algorithm.simple;/**
 * Project: springboot_test
 * Package: com.ljy.algorithm
 * Version: 1.0
 * Created by ljy on 2021-12-29 17:08
 */

/**
 * @ClassName MaxSumChildSeq
 * @Author: ljy on 2021-12-29 17:08
 * @Version: 1.0
 * @Description:最大子序和
 * 方案：动态规划
 * 思路：
 * 遍历数组，计算当前值，若当前值<历史值，保存当前值
 */
public class MaxSumChildSeq {
    public static void main(String[] args) {
        int[] nums = new int[]{-2,1,-3,4,-1,2,1,-5,4};
        int result = solution(nums);
        System.out.println(result);
    }
    public static int solution(int[] nums){

        int pre = 0,maxValue = nums[0];
        for (int num : nums) {
            pre = Math.max(pre+num,num);
            maxValue = Math.max(maxValue,pre);

        }
        return maxValue;
    }
}

package com.ljy.algorithm;/**
 * Project: springboot_test
 * Package: com.ljy.algorithm.the_sum_of_two_numbers
 * Version: 1.0
 * Created by ljy on 2021-12-27 8:47
 */

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName Solution
 * @Author: ljy on 2021-12-27 8:47
 * @Version: 1.0
 * @Description:
 * 需求：
 *  输入：nums = [2,7,11,15], target = 9
 *  输出：[0,1]
 *  解释：因为 nums[0] + nums[1] == 9 ，返回 [0, 1] 。
 * 实现：
 *  将nums存放到hashtable(num[i],i)
 *  hashtable.containKey(target-num[i])
 */
public class The_Sum_Of_Two_Numbers {
    public static void main(String[] args) {
        int[] nums = new int []{2,7,11,15};
        int target = 9;
        int[] result = twoSum(nums,target);
       System.out.println("["+result[0]+","+result[1]+"]");

    }

    public static int[] twoSum(int[] nums,int target){
        Map<Integer,Integer> hashtable = new HashMap<>();
        for (int i = 0;i < nums.length; ++i){

            if(hashtable.containsKey(target-nums[i])){
                return new int[]{hashtable.get(target - nums[i]),i};
            }
            hashtable.put(nums[i],i);
        }
        return  new int[0];
    }
}

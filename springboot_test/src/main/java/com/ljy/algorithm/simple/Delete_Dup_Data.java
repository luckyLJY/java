package com.ljy.algorithm.simple;/**
 * Project: springboot_test
 * Package: com.ljy.algorithm
 * Version: 1.0
 * Created by ljy on 2021-12-28 10:41
 */

/**
 * @ClassName:Delete_Dup_Data
 * @Author: ljy on 2021-12-28 10:41
 * @Version: 1.0
 * @Description:删除数组中的重复数据
 * 要求：
 *  不扩展其他空间，原地删除
 *  输入：nums = [1,1,2]
 * 输出：2, nums = [1,2]
 * 解释：函数应该返回新的长度 2 ，并且原数组 nums 的前两个元素被修改为 1, 2 。不需要考虑数组中超出新长度后面的元素。
 */
public class Delete_Dup_Data {
    public static void main(String[] args) {
        int[] nums = new int[]{1,1,1,3,4,5};
        int result = solution(nums);
        System.out.println("数量"+result);
        for (int i = 0; i < result; i++) {
            System.out.println(nums[i]);
        }
    }

    public static int solution(int[] nums){
        int n = nums.length;
        if (n == 0){
            return 0;
        }
        int fast = 1, slow = 1;
        while (fast < n){
            if (nums[fast] != nums[fast-1]){
                nums[slow] = nums[fast];
                ++slow;
            }
            ++fast;
        }
        return slow;
    }
}

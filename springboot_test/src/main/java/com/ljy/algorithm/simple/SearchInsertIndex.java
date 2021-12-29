package com.ljy.algorithm.simple;/**
 * Project: springboot_test
 * Package: com.ljy.algorithm
 * Version: 1.0
 * Created by ljy on 2021-12-29 15:27
 */

/**
 * @ClassName SearchInsertIndex
 * @Author: ljy on 2021-12-29 15:27
 * @Version: 1.0
 * @Description:搜索插入位置
 * 输入: nums = [1,3,5,6], target = 5
 * 输出: 2
 * 1 <= nums.length <= 104
 * -104 <= nums[i] <= 104
 * nums 为无重复元素的升序排列数组
 * -104 <= target <= 104
 */
public class SearchInsertIndex {
    public static void main(String[] args) {
        int[] nums =new int[]{1,2,3,5};
        int target = 3;
        int result = solution(nums,target);
        System.out.println(result);
    }

    public static  int solution(int[] nums,int target){
        int n = nums.length;
        int left = 0,right = n-1,ans =n;
        while (left<=right){
            int mid = ((right - left)>>1) +left;
            if (target <= nums[mid]){
                ans = mid;
                right = mid-1;
            }else {
                left = mid +1;
            }
        }
        return ans;
    }
}

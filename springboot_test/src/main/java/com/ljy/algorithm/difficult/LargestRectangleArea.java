package com.ljy.algorithm.difficult;/**
 * Project: springboot_test
 * Package: com.ljy.algorithm.difficult
 * Version: 1.0
 * Created by ljy on 2021-12-29 17:18
 */

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

/**
 * @ClassName:LargestRectangleArea
 * @Author: ljy on 2021-12-29 17:18
 * @Version: 1.0
 * @Description:柱状图中最大面积
 */
public class LargestRectangleArea {
    public static void main(String[] args) {
       int[] heights =  new int[]{2,1,5,6,2,3};
       int result =solution(heights);
        System.out.println(result);

    }

    public static int solution(int[] heights){
        int n = heights.length;
        int[] left = new int[n];
        int[] right = new int[n];
        Arrays.fill(right,n);

        Deque<Integer> mono_stack = new ArrayDeque<>();
        for (int i = 0;i < n;i ++){

            while (!mono_stack.isEmpty() && heights[mono_stack.peek()] >= heights[i]){
                right[mono_stack.peek()] = i;
                mono_stack.pop();
            }

            left[i] =(mono_stack.isEmpty()?-1:mono_stack.peek());
            mono_stack.push(i);
        }
        int ans =0;
        for (int i = 0;i < n;i ++){
            ans = Math.max(ans,(right[i]-left[i]-1)*heights[i]);
        }
        return ans;
    }

}

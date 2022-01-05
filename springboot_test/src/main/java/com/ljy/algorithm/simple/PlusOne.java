package com.ljy.algorithm.simple;/**
 * Project: springboot_test
 * Package: com.ljy.algorithm.simple
 * Version: 1.0
 * Created by ljy on 2022-1-5 15:13
 */

/**
 * @ClassName PlusOne
 * @Author: ljy on 2022-1-5 15:13
 * @Version: 1.0
 * @Description:
 * 示例1：
 * 输入：digits = [1,2,3]
 * 输出：[1,2,4]
 * 解释：输入数组表示数字 123。
 *
 * 示例2：
 * 输入：digits = [4,3,2,1]
 * 输出：[4,3,2,2]
 * 解释：输入数组表示数字 4321。
 *
 * 示例 3：
 * 输入：digits = [0]
 * 输出：[1]
 *
 *
 * 提示：
 * 1 <= digits.length <= 100
 * 0 <= digits[i] <= 9

 */
public class PlusOne {
    public static void main(String[] args) {
        int[] res=new int[]{1,2,9};
        int[] result = solution(res);
        for (int i = 0; i < result.length; i++) {
            System.out.println(result[i]);
        }
    }

    public static int[] solution(int[] digits){
        int n = digits.length;

        for (int i=n-1;i>=0;--i){
            if(digits[i]!=9){
                ++digits[i];
                for(int j=i+1;j<n;++j){
                    digits[j] = 0;
                }
                return digits;
            }
        }

        //digits中所有的元素均为9
        int[] ans = new int[n + 1];
        ans[0] = 1;
        return ans;
    }
}

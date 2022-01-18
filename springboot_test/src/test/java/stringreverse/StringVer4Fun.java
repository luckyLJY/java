package stringreverse;/**
 * Project: springboot_test
 * Package: com.ljy.algorithm.other.stringreverse
 * Version: 1.0
 * Created by ljy on 2022-1-17 10:04
 */


import org.junit.Test;

/**
 * @ClassName StringVer4Fun
 * @Author: ljy on 2022-1-17 10:04
 * @Version: 1.0
 * @Description:
 * TODO:String翻转的四种方法
 *  1. 使用字符串的charAt()方法反向拼接字符
 *  2. 将字符串遍历为字符数组，反向遍历，拼接为字符串
 *  3，使用字符串构建StringBuffer对象，使用reverse()方法翻转字符串
 *  4. 使用二分查找思想
 */
public class StringVer4Fun {

    /**
     * TODO: 使用字符串的charAt()方法反向拼接字符
     * @return
     */
    @Test
    public void  fun1(){
        String str = "abc";
        int templen = str.length();
        String reverse = " ";
        for (int i=0;i<templen;i++){
            reverse = str.charAt(i) + reverse;
        }
        System.out.println(reverse);
    }

    /**
     * TODO: 将字符串遍历为字符数组，反向遍历，拼接为字符串
     */
    @Test
    public void fun2(){

        String str = "abc";
        char[] array = str.toCharArray();
        String reverse = " ";
        for (int i = str.length()-1;i>=0;i--){
            reverse += array[i];
        }
        System.out.println(reverse);
    }

    /**
     * TODO: 使用字符串构建StringBuffer对象，使用reverse()方法翻转字符串
     */
    @Test
    public void fun3(){
        String str = "abc";
        StringBuffer sb = new StringBuffer(str);
        String reverString = sb.reverse().toString();
        System.out.println(reverString);
    }

    /**
     * TODO: 使用二分查找思想
     */
    public static String reverseRecursive(String s){
        int length = s.length();
        if(length<=1){
            return s;

        }
        String left  = s.substring(0,length/2);
        String right = s.substring(length/2 ,length);
        String afterReverse = reverseRecursive(right)+reverseRecursive(left);//此处是递归的方法调用
        return afterReverse;
    }
}

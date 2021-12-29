package com.ljy.algorithm.simple;/**
 * Project: springboot_test
 * Package: com.ljy.algorithm
 * Version: 1.0
 * Created by ljy on 2021-12-28 8:37
 */

/**
 * @ClassName:Merge_Order_Link_List
 * @Author: ljy on 2021-12-28 8:37
 * @Version: 1.0
 * @Description:合并有序链表
 * 输入：l1 = [1,2,4], l2 = [1,3,4]
 * 输出：[1,1,2,3,4,4]
 * 两个链表的节点数目范围是 [0, 50]
 * -100 <= Node.val <= 100
 * l1 和 l2 均按 非递减顺序 排列
 * 思路：
 *  遍历一个集合在另一个集合中寻找比当前元素小的元素，添加到当前元素之前
 * 方法：
 *  1. 分别建两个链表存储数据
 *  2. 链表的结果总是指向下一个链表
 */
public class Merge_Order_Link_List {
    public static void main(String[] args) {

        ListNode l1 = new ListNode(1);
        l1.add(2);
        l1.add(4);


        ListNode l2 = new ListNode(1);;
        l2.add(3);
        l2.add(4);

        ListNode l3 = solution1(l1,l2);

        l3.print();

    }

    /**
     * 递归遍历比较两个列表
     * @param l1
     * @param l2
     * @return
     */
    public static  ListNode solution(ListNode l1, ListNode l2) {
        if (l1 == null){
            return l2;
        }else if (l2 == null){
            return l1;
        }else if (l1.data < l2.data){
            l1.next = solution(l1.next,l2);
            return l1;
        }else {
            l2.next =solution(l1,l2.next);
            return l2;
        }
    }

    /**
     * 建立第三个空列表
     * 将前两个列表比较的最小值依次加入第三个列表
     * @param l1
     * @param l2
     * @return
     */
    public static ListNode solution1(ListNode l1, ListNode l2){
        ListNode prehead = new ListNode(-1);
        ListNode prev = prehead;
        while (l1!=null && l2!=null){
            if (l1.data <= l2.data){
                prev.next = l1;
                l1 = l1.next;
            }else {
                prev.next = l2;
                l2 = l2.next;
            }
            prev =prev.next;
        }
        prev.next =l1 ==null ? l2:l1;
        return prehead.next;
    }
}

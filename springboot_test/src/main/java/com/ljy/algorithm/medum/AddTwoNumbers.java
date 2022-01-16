package com.ljy.algorithm.medum;/**
 * Project: springboot_test
 * Package: com.ljy.algorithm.difficult
 * Version: 1.0
 * Created by ljy on 2022-1-10 14:46
 */

import com.ljy.algorithm.simple.ListNode;

/**
 * @ClassName addTwoNumbers
 * @Author: ljy on 2022-1-10 14:46
 * @Version: 1.0
 * @Description:
 * 使用链表实现两数之和
 * 输入：l1 = [2,4,3], l2 = [5,6,4]
 * 输出：[7,0,8]
 * 解释：342 + 465 = 807.
 */
public class AddTwoNumbers {
    public static void main(String[] args) {
    ListNode l1 = new ListNode(2);
    l1.add(4);
    l1.add(3);

    ListNode l2 = new ListNode(5);
    l2.add(6);
    l2.add(4);

    ListNode l3 = new ListNode();
    l3 = solution(l1,l2);
    l3.print();

    }

    public static ListNode solution(ListNode l1, ListNode l2){
        ListNode head=null,tail=null;
        int caryy =0;
        while (l1!=null || l2!=null){
            int n1 = l1 !=null ? l1.data :0;
            int n2 = l2 !=null ?l2.data:0;

            int sum = n1 + n2 +caryy;
            if (head==null){
                head = tail = new ListNode(sum % 10);
            }else {
                tail.next = new ListNode(sum%10);
                tail = tail.next;
            }

            caryy = sum / 10;
            if (l1!=null){
                l1 = l1.next;
            }
            if (l2!=null){
                l2 =l2.next;
            }

        }
        if (caryy>0){
            tail.next = new ListNode(caryy);
        }

        return head;
    }
}

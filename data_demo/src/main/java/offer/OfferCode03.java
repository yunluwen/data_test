package offer;

import java.util.ArrayList;
import java.util.List;

/**
 * 倒序
 * 输入一个链表，按链表值从尾到头的顺序返回一个ArrayList。
 */
public class OfferCode03 {

    public ArrayList<Integer> printListFromTailToHead(ListNode listNode) {
        List<Integer> list = new ArrayList<Integer>();
        if(listNode.next == null){
            while(listNode.next != null){
                list.add(listNode.val);
                listNode = listNode.next;
            }
        }


        return null;
    }
}

class ListNode {
    int val;
    ListNode next = null;

     ListNode(int val) {
          this.val = val;
     }
}
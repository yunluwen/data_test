package offer;

import datastatus.tree.one.BinaryTreeNode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * 从上到下按层打印二叉树，同一层结点从左至右输出。每一层输出一行。
 */
public class Test3 {

    ArrayList<ArrayList<Integer>> Print(BinaryTreeNode pRoot) {
        BinaryTreeNode temp;
        ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();
        Queue<BinaryTreeNode> queue = new LinkedList<>();
        if(pRoot == null)
            return result;
        queue.add(pRoot);
        ArrayList<Integer> list = new ArrayList<>();
        int start = 0, end = 1;
        /**
         * 有点迷啊
         */
        while (!queue.isEmpty()) {
            temp = queue.poll();     //移除并返问队列头部的元素，1
            list.add(temp.getData());
            start++;
            if (temp.left != null) {
                queue.add(temp.left);
            }
            if (temp.right != null) {
                queue.add(temp.right);
            }
            if(start == end){
                end = queue.size();    //队列的大小2
                start = 0;
                result.add(list);
                list = new ArrayList<Integer>();
            }
        }
        return result;
    }
}

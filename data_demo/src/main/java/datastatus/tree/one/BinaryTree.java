package datastatus.tree.one;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * 递归遍历方法
 */
public class BinaryTree {

    private BinaryTreeNode root;//二叉树根结点

    public BinaryTree() {
        this.root = null;
    }

    /*       建立二叉树：
                 1
               /   \
              2      3
             / \    /
            4   5   6
     */
    public BinaryTreeNode createBinaryTree() {
        root = new BinaryTreeNode(1);
        root.setLeft(new BinaryTreeNode(2));
        root.setRight(new BinaryTreeNode(3));
        root.getLeft().setLeft(new BinaryTreeNode(4));
        root.getLeft().setRight(new BinaryTreeNode(5));
        root.getRight().setLeft(new BinaryTreeNode(6));
        return root;
    }

    //先序遍历：递归方法
    public void PreOrderRecursive(BinaryTreeNode root) {
        if(root == null)
            return;
        System.out.print(root.getData() + " ");//访问根结点
        PreOrderRecursive(root.getLeft());//先序遍历其左子树
        PreOrderRecursive(root.getRight());//先序遍历其右子树
    }

    //中序遍历：递归方法
    public void InOrderRecursive(BinaryTreeNode root) {
        if(root == null)
            return;
        InOrderRecursive(root.getLeft());//中序遍历其左子树
        System.out.print(root.getData() + " ");//访问根结点
        InOrderRecursive(root.getRight());//中序遍历其右子树
    }

    //后序遍历：递归方法
    public void PostOrderRecursive(BinaryTreeNode root) {
        if(root == null)
            return;
        PostOrderRecursive(root.getLeft());//中序遍历其左子树
        PostOrderRecursive(root.getRight());//中序遍历其右子树
        System.out.print(root.getData() + " ");//访问根结点
    }

    //先序遍历:非递归方法，根左右
    public void PreOrderIterative(BinaryTreeNode root) {
        BinaryTreeNode temp = root;
        Stack<BinaryTreeNode> s = new Stack<BinaryTreeNode>();
        while (temp != null || !s.isEmpty()) {
            while (temp != null) {  //遇到一个结点，就把它压栈并访问它，然后去遍历它的左子树；
                s.push(temp);   //
                System.out.print(temp.getData() + " ");  //1，2，4
                temp = temp.getLeft();   //左孩子
            }
            if(!s.isEmpty()) {
                temp = s.pop();//当左子树遍历结束后，从栈顶弹出这个结点；
                temp = temp.getRight();//然后按其右指针再去先序遍历该结点的右子树，右孩子
            }
        }
    }

    //中序遍历:非递归方法，左根右
    public void InOrderIterative(BinaryTreeNode root) {
        BinaryTreeNode temp = root;
        Stack<BinaryTreeNode> s = new Stack<BinaryTreeNode>();
        while (temp != null || !s.isEmpty()) {
            while (temp != null) {  //遇到一个结点，就把它压栈，并去遍历它的左子树；
                s.push(temp);
                temp = temp.getLeft();  //左孩子，1，2，4，3，6
            }
            if(!s.isEmpty()) {
                temp = s.pop();//当左子树遍历结束后，从栈顶弹出这个结点并访问它；
                System.out.print(temp.getData() + " ");  //4，2，5，1，6，3
                temp = temp.getRight();// 然后按其右指针再去中序遍历该结点的右子树
            }
        }
    }

    //后序遍历:非递归方法，左右根(重点)
    public void PostOrderIterative(BinaryTreeNode root) {
        BinaryTreeNode temp = root;
        BinaryTreeNode pre = null;//标记访问序列中前一个二叉树节点（当前节点的之前访问的节点）
        Stack<BinaryTreeNode> s = new Stack<BinaryTreeNode>();
        while (temp != null || !s.isEmpty()) {
            while (temp != null) {
                s.push(temp);         //5
                temp = temp.getLeft();  //左孩子:进栈顺序，第一次  1，2，4    第二次  5   第三次  3，6
            }
            if(!s.isEmpty()) {
                temp = s.peek();   //获取栈顶，不删除栈顶 4 ，5，2，1,6
                //如果一个节点右孩子是空，或者右孩子刚被访问过，那么就访问该节点。否则就往右孩子走。
                if(temp.getRight() == null || temp.getRight() == pre) {  //2的右孩子是5
                    System.out.print(temp.getData() + " ");  //4，5，2，6,3,1
                    s.pop();       //1,3
                    pre = temp;    //3
                    temp = null;
                }else {
                    temp = temp.getRight();  //5，3
                }
            }
        }
    }

    //层序遍历
    public void LevelOrder(BinaryTreeNode root) {
        BinaryTreeNode temp;
        Queue<BinaryTreeNode> q = new LinkedList<>();  //队列，按顺序出来
        if(root == null)
            return;
        q.add(root);
        while (!q.isEmpty()) {
            temp = q.poll();   //移除并返问队列头部的元素，1
            System.out.print(temp.getData() + " ");  //1，2，3，4，5，6
            if (temp.getLeft() != null) {
                q.add(temp.getLeft());//左孩子入队
            }
            if (temp.getRight() != null) {
                q.add(temp.getRight());//右孩子入队
            }
        }
    }


    public static void main(String[] args) {

        //递归遍历
        BinaryTree tree = new BinaryTree();
        BinaryTreeNode root = tree.createBinaryTree();
        System.out.println("先序遍历为：");
        tree.PreOrderRecursive(root);
        System.out.println("\n中序遍历为：");
        tree.InOrderRecursive(root);
        System.out.println("\n后序遍历为：");
        tree.PostOrderRecursive(root);

        System.out.println("\n\n");
        //非递归遍历
        System.out.println("先序遍历为：");
        tree.PreOrderIterative(root);
        System.out.println("\n中序遍历为：");
        tree.InOrderIterative(root);
        System.out.println("\n后序遍历为：");
        tree.PostOrderIterative(root);
        System.out.println("\n层序遍历为：");
        tree.LevelOrder(root);

    }

}

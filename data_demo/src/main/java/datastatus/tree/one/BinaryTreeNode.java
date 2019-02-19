package datastatus.tree.one;

/**
 * 二叉树节点
 */
public class BinaryTreeNode {

    private int data;//结点的数据
    public BinaryTreeNode left;//指向左孩子结点
    public BinaryTreeNode right;//指向左孩子结点

    public BinaryTreeNode(int data) {
        this.data = data;
        this.left = null;
        this.right =null;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public BinaryTreeNode getLeft() {
        return left;
    }

    public void setLeft(BinaryTreeNode left) {
        this.left = left;
    }

    public BinaryTreeNode getRight() {
        return right;
    }

    public void setRight(BinaryTreeNode right) {
        this.right = right;
    }

}

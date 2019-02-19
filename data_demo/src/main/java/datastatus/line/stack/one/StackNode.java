package datastatus.line.stack.one;

public class StackNode {

    private int node;
    private StackNode next;

    public StackNode(int node) {
        this.node = node;
//        this.next = null;
    }

    public int getNode() {
        return node;
    }

    public StackNode getNext() {
        return next;
    }

    public void setNode(int node) {
        this.node = node;
    }

    public void setNext(StackNode next) {
        this.next = next;
    }
}

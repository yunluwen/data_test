package datastatus.line.linkedlist.three.first;

public class SCLLNode {
    private int data;
    private SCLLNode next;

    public SCLLNode(int data) {
        this.data = data;
    }

    public void display() {
        System.out.print( data + " ");
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public SCLLNode getNext() {
        return next;
    }

    public void setNext(SCLLNode next) {
        this.next = next;
    }
}

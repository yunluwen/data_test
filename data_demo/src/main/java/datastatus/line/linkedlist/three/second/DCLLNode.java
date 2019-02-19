package datastatus.line.linkedlist.three.second;

public class DCLLNode {

    private int data;
    private DCLLNode next;
    private DCLLNode previous;

    public DCLLNode(int data, DCLLNode next, DCLLNode previous) {
        this.data = data;
        this.next = next;
        this.previous = previous;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public DCLLNode getNext() {
        return next;
    }

    public void setNext(DCLLNode next) {
        this.next = next;
    }

    public DCLLNode getPrevious() {
        return previous;
    }

    public void setPrevious(DCLLNode previous) {
        this.previous = previous;
    }

    public void display() {
        System.out.print( data + " ");
    }

}

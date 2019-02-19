package datastatus.line.queue.one;

public class QueueNode {

    private int data;
    private QueueNode next;

    public QueueNode(int data) {
        this.data = data;
        this.next = null;
    }

    public int getData() {
        return data;
    }

    public QueueNode getNext() {
        return next;
    }

    public void setData(int data) {
        this.data = data;
    }

    public void setNext(QueueNode next) {
        this.next = next;
    }
}

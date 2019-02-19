package datastatus.line.queue.one;

/**
 * 单链表实现的队列
 */
public class LinkQueue {

    private QueueNode front;    //头
    private QueueNode rear;     //尾

    public LinkQueue() {
        this.front = null;
        this.rear = null;
    }

    //判断队列中是否有元素
    public boolean isEmpty() {
        return front == null;
    }


    //入队操作：在队列的队尾插入一个元素
    public void enQueue(int data) {
        QueueNode newNode = new QueueNode(data);
        if(isEmpty()) {
            front = newNode;
            rear = newNode;
        }else {
            rear.setNext(newNode);    //在队尾添加
            rear = newNode;
        }
    }

    //出队操作：删除并返回队首的元素
    public int deQueue() {
        if(isEmpty()) {
            System.out.println("错误：队列为空");
            return 0;
        }else {
            int data = front.getData();
            front = front.getNext();    //删除队头
            return data;
        }
    }

    public static void main(String[] args) {
        LinkQueue q = new LinkQueue();
        q.enQueue(1);
        q.enQueue(2);
        q.enQueue(3);
        System.out.println(q.deQueue());
        System.out.println(q.isEmpty());

    }

}

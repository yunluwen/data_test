package datastatus.line.queue;

public class CircularArrayQueue {

    private int front;//队首下标
    private int rear;//队尾下标
    private int[] array;//用数组实现队列
    private int capacity;//容量

    public CircularArrayQueue(int capacity) {
        this.capacity = capacity;
        array = new int[capacity];
        front = 0;
        rear = 0;
    }

    //判断队列中是否有元素
    public boolean isEmpty() {
        return front == rear;
    }

    //判断队列中是否存满元素
    public boolean isFull() {
        return ((rear + 1) % capacity == front);
    }

    //入队操作：在队列的队尾插入一个元素
    public void enQueue(int data) {
        if(isFull()) {
            System.out.println("错误：队列已满");
        }else {
            array[rear] = data;
            rear = (rear + 1) % capacity;
        }
    }

    //出队操作：删除并返回队首的元素
    public int deQueue() {
        if(isEmpty()) {
            System.out.println("错误：队列为空");
            return 0;
        }else {
            int data = array[front];
            front = (front + 1) % capacity;
            return data;
        }
    }

    public static void main(String[] args) {
        CircularArrayQueue q = new CircularArrayQueue(6);
        q.enQueue(1);
        q.enQueue(2);
        q.enQueue(3);
        q.enQueue(4);
        System.out.println(q.deQueue());
        System.out.println(q.isEmpty());
        System.out.println(q.isFull());
    }

}

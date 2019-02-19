package datastatus.line.linkedlist.two;

public class TwoLinkedList<T> {

    private int size;
    private Node<T> header;
    private Node<T> tail;

    public TwoLinkedList() {
        this.size = 0;
        header = new Node<T>(null,null,null);
        tail = new Node<T>(null,header,null);
        header.next = tail;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    public int size(){
        return size;
    }

    public void add(T item){
        Node<T> newNode = new Node(item,null,null);
        tail.pre.next = newNode;
        newNode.pre = tail.pre;
        newNode.next = tail;
        tail.pre = newNode;

//        newNode.pre = tail;
//        newNode.next = tail.next;
//        tail.next = newNode;
        size++;
    }

    public T get(int idx){
        if(idx >= size || idx < 0){
            throw new IndexOutOfBoundsException();
        }
        Node current = header;
        for(int i=0; i<=idx; i++){
            current = current.next;
        }
        return (T)current.data;
    }

    public void print(){
        Node<T> current = header.next;
        while(current.next != null){
            System.out.println(current.data.toString());
            current = current.next;
        }
    }

    public static void main(String[] args) {
        TwoLinkedList<String> strs = new TwoLinkedList<String>();
        strs.add("first");
        strs.add("second");
        strs.add("three");
        System.out.println("size :" + strs.size);
        strs.print();
        System.out.println(strs.get(0));

    }
}

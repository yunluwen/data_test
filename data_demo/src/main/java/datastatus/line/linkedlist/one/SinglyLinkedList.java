package datastatus.line.linkedlist.one;

public class SinglyLinkedList {

    private Node head;  //head
    private int length = 0;

    public SinglyLinkedList(){
        this.head = null;
    }

    public void addHead(int data) {
        Node node = new Node(data);
        if(head == null){
            head = node;
        }else{
            node.setNext(head);
        }
        length++;
    }

    public Node deleteHead() {
        if(head == null){
            return head;
        }
        Node curNode = head;
        head = curNode.getNext();
        return curNode;
    }


    public void addTail(int data) {
        Node newNode = new Node(data);
        if(head == null){
            head = newNode;
        }else {
            Node pre = head;
            int count = 1;
            while(count<length){
                pre = pre.getNext();
                count++;
            }
            pre.setNext(newNode);
        }
        length++;
    }


    public Node deleteTail() {
        if(head == null){
            return head;
        }
        Node preNode = head;
        int count = 1;
        while(count < length-1) {
            preNode = preNode.getNext();
            count++;
        }
        Node curNode = preNode.getNext();//指向最后一个结点
        preNode.setNext(curNode.getNext());//指向最后一个结点next指针值null
        length--;
        return null;
    }

    public void printAllNode() {
        Node cur = head;
        while (cur != null) {
            cur.display();//显示此结点的数据
            cur = cur.getNext();
        }
        System.out.println();
    }

    public int listLength() {
        return length;
    }

    public void insertList(int data, int index) {
        Node newNode = new Node(data);
        if(head == null){
            head = newNode;//链表为空，插入
        }
        if(index > length+1 || index < 1) {
            System.out.println("结点插入的位置不存在，可插入的位置为1到"+(length+1));
        }
        if(index == 1) {
            newNode.setNext(head);
            head = newNode;//在链表开头插入
        }
        else {             //在链表中间或尾部插入
            Node preNode = head;
            int count = 1;
            while(count < index-1) {
                preNode = preNode.getNext();
                count++;
            }
            Node curNode = preNode.getNext();
            newNode.setNext(curNode);
            preNode.setNext(newNode);
        }
    }

    public Node deleteList(int index) {
        return null;
    }

    public boolean containData(int data) {
        return false;
    }

    public void getIndexData(int index) {

    }

    public void updateIndexData(int index, int data) {

    }

}

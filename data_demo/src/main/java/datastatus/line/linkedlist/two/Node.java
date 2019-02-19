package datastatus.line.linkedlist.two;

public class Node<T>{

    public Node<T> pre;   //头指针
    public Node<T> next;  //尾指针
    public T data;        //数据域

    public Node(){
        this.data = null;
        this.next = null;
        this.pre = null;
    }
    public Node(T data,Node pre, Node next){
        this.data = data;
        this.pre = pre;
        this.next = next;
    }

    public Node<T> getPre() {
        return pre;
    }

    public void setPre(Node<T> pre) {
        this.pre = pre;
    }

    public Node<T> getNext() {
        return next;
    }

    public void setNext(Node<T> next) {
        this.next = next;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
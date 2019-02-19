package datastatus.line.linkedlist.three.first;

/**
 * 单向循环
 */
public class SinglyCircularLinkedList {

    private SCLLNode head;//表头
    private int length = 0;

    public SinglyCircularLinkedList() {
        this.head = null;
    }

    //在链表头部添加结点
    public void addHead(int data) {
        SCLLNode newNode = new SCLLNode(data);
        newNode.setNext(newNode);
        if (head == null) {
            head = newNode;
        } else {
            SCLLNode curNode = head;
            //找到最后一个节点
            while (curNode.getNext() != head) {
                curNode = curNode.getNext();
            }
            //将插入节点的指针指向旧的表头
            //设置最后一个节点指向新节点
            newNode.setNext(head);
            curNode.setNext(newNode);
            //设置表头为新插入的节点
            head = newNode;
        }

        length++;
    }

    //在链表头部删除结点
    public void deleteHead() {
        if (head == null) {
            System.out.println("空表，删除的结点不存在");
        }
        SCLLNode curNode = head;
        SCLLNode temp = head;
        //找到最后一个节点
        while (curNode.getNext() != head) {
            curNode = curNode.getNext();
        }
        //设置最后一个节点的指针指向表头的下一个节点
        curNode.setNext(head.getNext());
        //设置表头为旧表头的下一个节点
        head = head.getNext();
        temp = null;       //将旧的头节点占用的空间释放掉
        length--;
    }

    //在链表尾部添加结点
    public void addTail(int data) {
        SCLLNode newNode = new SCLLNode(data);
        newNode.setNext(newNode);
        if (head == null) {
            head = newNode;
        } else {
            SCLLNode curNode = head;
            //找到最后一个节点
            while (curNode.getNext() != head) {
                curNode = curNode.getNext();
            }
            //先设置内部的，再设置外部的，否则链无法连接起来
            newNode.setNext(head);
            curNode.setNext(newNode);
        }
        length++;
    }

    //在链表尾部删除结点
    public void deleteTail() {
        if (head == null) {
            System.out.println("空表，删除的结点不存在");
        }
        SCLLNode curNode = head;
        SCLLNode preNode =head;
        //找到最后一个节点
        while (curNode.getNext() != head) {
            preNode = curNode;        //最后一个节点的上一个节点
            curNode = curNode.getNext();
        }
        preNode.setNext(head);
        curNode=null;      //将这个对象占用的空间释放掉
        length--;
    }

    //在指定位置插入结点
    public void insertList(int data, int index) {
        SCLLNode newNode = new SCLLNode(data);
        newNode.setNext(newNode);
        if(head == null){
            head = newNode;
        }
        if(index > length+1 || index < 1) {
            System.out.println("结点插入的位置不存在，可插入的位置为1到"+(length+1));
        }
        if(index == 1){
            //在头部添加数据
            addHead(data);
        }else {
            //在中间或尾部添加数据
            SCLLNode preNode = head;
            int count = 1;
            while(count < index-1) {
                preNode = preNode.getNext();   //要插入位置的上一个节点
                count++;
            }
            SCLLNode curNode = preNode.getNext();   //获取要插入的这个位置的数据
            //将新节点添加到这两个数据之间 //先设置内部的，再设置外部的，否则链无法连接起来
            newNode.setNext(curNode);
            preNode.setNext(newNode);
        }
        length++;
    }

    //在指定位置删除结点
    public void deleteList(int index) {
        if(index > length+1 || index < 1) {
            System.out.println("结点插入的位置不存在，可插入的位置为1到"+(length+1));
        }
        if(index == 1){
            //在头部删除节点
            deleteHead();
        }else {
            //在中间或尾部删除数据
            SCLLNode preNode = head;
            int count = 1;
            while(count < index-1) {
                preNode = preNode.getNext();   //要删除位置的上一个节点
                count++;
            }
            SCLLNode curNode = preNode.getNext();   //获取要删除的这个位置
            SCLLNode temp = preNode.getNext();
            SCLLNode nextNode = curNode.getNext();
            preNode.setNext(nextNode);
            temp = null;
        }
        length--;
    }

    //查找数据是否存在
    public boolean containData(int data) {
        if(head == null){
            System.out.println("空表");
            return false;
        }
        SCLLNode curNode = head;
        while(curNode.getData()!= data){
            if(curNode.getNext() == head) {
                System.out.println("结点数据不存在");
                return false;
            }
            curNode =curNode.getNext();
        }
        System.out.println("结点数据存在");
        return true;
    }

    //获取指定位置的数据
    public void getIndexData(int index) {
        if(head == null){
            System.out.println("空表");
        }
        if(index > length || index < 1) {
            System.out.println("结点位置不存在，可获取的位置为1到"+length);
        }else {
            SCLLNode curNode = head;
            int count =1;
            while(count != index) {
                curNode =curNode.getNext();
                count++;
            }
            curNode.display();
            System.out.println();
        }
    }

    //修改指定位置的结点数据
    public void updateIndexData(int index, int data) {
        if(head == null){
            System.out.println("空表");
        }
        if(index > length || index < 1) {
            System.out.println("结点位置不存在，可更新的位置为1到"+length);
        }else {
            SCLLNode curNode = head;
            int count =1;//while也可以用for循环方式解决
            while(count != index) {
                curNode =curNode.getNext();
                count++;
            }
            curNode.setData(data);
        }

    }

    //遍历链表
    public void printAllNode() {
        SCLLNode cur = head;
        while (cur != null) {
            cur.display();
            cur = cur.getNext();
            if(cur == head) {
                break;
            }
        }
        System.out.println();

    }

    //获取链表的长度
    public int  listLength() {
        return length;
    }


}

















package datastatus.line.stack.one;

public class LinkStack {

    private StackNode top;
    private int length = 0;

    public LinkStack() {
        this.top = null;
    }

    public boolean isEmpty(){
        if(top == null){
            return true;
        }
        return false;
    }

    public void push(int data){
        StackNode stackNode = new StackNode(data);
        if(isEmpty()){
            this.top = stackNode;
        }else{
            stackNode.setNext(top);
            this.top = stackNode;
        }
        this.length++;
    }

    public int pop(){
        if(isEmpty()){
            return 0;
        }
        int data = this.top.getNode();
        top = top.getNext();
        this.length--;
        return data;
    }

    public int top() {
        if(isEmpty()) {
            System.out.println("栈为空");
            return 0;
        }else {
            return top.getNode();
        }
    }

    public int size(){
        return this.length;
    }

    //删除栈
    public void deleteStack(){
        this.top = null;
    }

    public static void main(String[] args) {
        LinkStack linkStack = new LinkStack();
        linkStack.push(1);
        linkStack.push(2);
        linkStack.push(3);
        linkStack.push(4);
        linkStack.push(5);
        System.out.println(linkStack.size());
        System.out.println(linkStack.pop());
        System.out.println(linkStack.pop());
        System.out.println(linkStack.size());
        System.out.println(linkStack.pop());
    }
}





















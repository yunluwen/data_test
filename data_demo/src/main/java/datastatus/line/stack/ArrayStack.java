package datastatus.line.stack;

/**
 * 先进后出，后进先出
 */
public class ArrayStack {

    private int top;//栈顶元素的位置
    private int capacity;//栈的容量
    private int[] array;//实现栈功能的数组

    public ArrayStack(int capacity) {
        this.capacity = capacity;
        array = new int[capacity];
        top = -1;
    }

    public int getTop() {
        return top;
    }

    public int getCapacity() {
        return capacity;
    }

    //判断栈中是否有元素
    public boolean isEmpty() {
        return top == -1;
    }

    //判断栈中是否存满元素
    public boolean isStackFull() {
        return (top == capacity - 1);
    }

    //入栈：将数据压入栈
    public void push(int data) {
        if(isStackFull()) {
            System.out.println("栈已满");
            doubleStack();          //扩容
        }else {
            array[++top] = data;    // top = top +1; array[top] = data;
        }
    }

    //栈的大小扩容：数组倍增
    public void doubleStack() {
        int newArray[] = new int[2 * capacity];
        System.arraycopy(array,0,newArray,0,capacity);
        capacity = capacity * 2;
        array = newArray;
    }


    //出栈：删除并返回最后一个插入栈的元素
    public int pop() {
        if(isEmpty()) {
            System.out.println("栈为空");
            return 0;
        }else {
            return array[top--]; //array[top] = data;top = top -1 ;
        }
    }

    //获取栈顶的元素,但不出栈
    public int top() {
        return array[getTop()];
    }

    //返回栈中元素的个数
    public int size() {
        return (top + 1);
    }

    //删除栈
    public void deteleStack() {
        top = -1;
    }

    public static void main(String[] args) {
        ArrayStack stack = new ArrayStack(6);
        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(4);
        System.out.println(stack.pop());
        System.out.println(stack.size());
    }

}

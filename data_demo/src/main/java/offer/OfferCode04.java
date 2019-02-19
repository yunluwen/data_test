package offer;

import java.util.Stack;

/**
 * 用两个栈来实现一个队列，完成队列的Push和Pop操作。 队列中的元素为int类型。
 */
public class OfferCode04 {

    Stack<Integer> stack1 = new Stack<Integer>();
    Stack<Integer> stack2 = new Stack<Integer>();

    public void push(int node) {
        stack1.push(node);
    }

    public int pop() {
        while(!stack1.isEmpty()){
            stack2.push(stack1.pop());
        }
        int first=stack2.pop();
        while(!stack2.isEmpty()){
            stack1.push(stack2.pop());
        }
        return first;
    }

    public static void main(String[] args) {
        OfferCode04 offerCode04 = new OfferCode04();
        offerCode04.push(1);
        offerCode04.push(2);
        offerCode04.push(3);
        System.out.println(offerCode04.pop());
        System.out.println(offerCode04.pop());
        offerCode04.push(4);
        System.out.println(offerCode04.pop());
    }
}

package offer;

import java.util.EmptyStackException;
import java.util.Iterator;
import java.util.Stack;

/**
 * 求栈的最小值
 *
 * Stack.Peek 与 stack.pop 的区别
 * 相同点：大家都返回栈顶的值
 * 不同点：peek 不改变栈的值(不删除栈顶的值)，pop会把栈顶的值删除
 */
public class AddMinFunctionForStack {

    /**
     * @description 使用单个私有栈
     */
    private Stack<Integer> stack = new Stack<>();

    public int minStack(){
        if(stack.isEmpty()){    //如果栈空，执行peek或pop出现异常:java.util.EmptyStackException
            try {
                throw new EmptyStackException();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        int i = stack.peek();
        Iterator<Integer> iterator = stack.iterator();
        while(iterator.hasNext()){
            int next = iterator.next();
            if(next<i){
                i = next;
            }
        }
        return i;
    }

    public static void main(String[] args) {
        AddMinFunctionForStack addMinFunctionForStack = new AddMinFunctionForStack();
//        addMinFunctionForStack.stack.peek();
        addMinFunctionForStack.stack.push(6);
        addMinFunctionForStack.stack.push(5);
        addMinFunctionForStack.stack.push(1);
        addMinFunctionForStack.stack.push(2);
        addMinFunctionForStack.stack.push(3);
//        addMinFunctionForStack.stack.pop();
        System.out.println(addMinFunctionForStack.minStack());
    }

}

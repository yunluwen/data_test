package error;

/**
 * 栈溢出
 * Exception in thread "main" java.lang.StackOverflowError
 */
public class StackError {

    public static void main(String[] args) {
        new StackError().test();
    }

    public void test(){
        test();
    }

}

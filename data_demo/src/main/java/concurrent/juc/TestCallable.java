package concurrent.juc;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * 创建执行线程的方式3
 * 实现Callable<T> 接口
 */
public class TestCallable {

    public static void main(String[] args) {
        ThreadTest threadTest = new ThreadTest();

        //1、执行Callable方式，需要FutureTask实现类的支持，用于接收运算结果
        FutureTask<Integer> result = new FutureTask<>(threadTest);
        new Thread(result).start();


        //2、接收线程运算后的结果
        try {
            //在线程运行的过程中，这个结果不会执行，是在线程执行完成之后获得的结果
            Integer sum = result.get();   //FutureTask可用于闭锁
            System.out.println(sum);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

}

class ThreadTest implements Callable<Integer> {

    @Override
    public Integer call() {
        int sum = 0;
        for(int i=0;i<10;i++){
            System.out.println(i);
            sum += i;
        }
        return sum;
    }

}

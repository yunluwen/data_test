package concurrent.juc;

import java.util.Random;
import java.util.concurrent.*;

/**
 * 线程调度
 *
 * 可以线程调度的线程池
 */
public class TestScheduledThreadPool {

    public static void main(String[] args) throws Exception {
        ScheduledExecutorService pool = Executors.newScheduledThreadPool(5);

        for(int i=0;i<10;i++) {
            //第一个参数传入线程实例，第二个参数是时间，第三个参数是时间类型(天/时/分/秒)
            Future<Integer> result = pool.schedule(new Callable<Integer>() {
                @Override
                public Integer call() throws Exception {
                    int num = new Random().nextInt(100);   //产生一个100以内的随机数
                    System.out.println(Thread.currentThread().getName() + ":" + num);
                    return num;
                }
            }, 3, TimeUnit.SECONDS);    //延迟多长时间执行

            System.out.println(result.get());
        }

        pool.shutdown();

    }

}

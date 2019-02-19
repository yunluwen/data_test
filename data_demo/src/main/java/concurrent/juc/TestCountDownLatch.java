package concurrent.juc;

import org.apache.derby.iapi.services.locks.Latch;

import java.util.concurrent.CountDownLatch;

/**
 * CountDownLatch ： 闭锁
 * 在完成某些运算时，只有其他所有线程的运算全部完成，当前运算才继续执行
 */
public class TestCountDownLatch {

    public static void main(String[] args) {
        CountDownLatch countDownLatch = new CountDownLatch(5);
        LatchDemo latchDemo = new LatchDemo(countDownLatch);

        long start = System.currentTimeMillis();

        for(int i=0;i<5;i++){
            new Thread(latchDemo).start();
        }

        try {
            countDownLatch.await();   //所有线程执行完一定会执行到这里
        }catch (Exception e){
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        System.out.println("执行时间："+ (end-start));

    }
}

class LatchDemo implements Runnable {

    private CountDownLatch latch;

    public LatchDemo(CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    public void run() {

        try {
            for(int i=0;i<50000;i++){
                if(i%2 == 0){
                    System.out.println(i);
                }
            }
        }finally {
            latch.countDown();
        }

    }
}














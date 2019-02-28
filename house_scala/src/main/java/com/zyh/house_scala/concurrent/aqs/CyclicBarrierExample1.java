package com.zyh.house_scala.concurrent.aqs;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * CyclicBarrier
 * 运行一组线程等待到一个公共的屏障点，实现多个线程相互等待，
 * 当每一个线程都就绪后，才执行下去，通过计数器实现的多线程计算数据，
 * 最后合并的场景
 *
 * CyclicBarrier 与CountDownLatch 的区别
 * 1  CountDownLatch 的计数器只能使用一次，CyclicBarrier 可以使用reset()方法重置
 * 2 CountDownLatch 实现一个或者n个线程需要等待其他线程执行某项操作后才能继续执行  
 *
 * CyclicBarrier  实现多个线程了多个线程相互等待，
 * 直到多个线程都满足了某个条件以后才继续执行描述的多个线程内部的关系，
 * 多个线程都调用await()方法后才继续向下执行提供方法获取阻塞线程的个数，
 * 直到阻塞的线程是否中断
 *
 * CyclicBarrier  对象调用await() 等待多个线程都满足条件后，再往下面执行
 *
 * //定义有5个线程同步等待
 * 1) private static CyclicBarrierbarrier =new CyclicBarrier(5);
 * 2) 在5个线程都满足条件后，先执行 log.info("callback is running");
 *    再执行以后的代码
 *    private static CyclicBarrierbarrier =new CyclicBarrier(5, () -> {log.info("callback is running");});
 *
 */
public class CyclicBarrierExample1 {

    private static CyclicBarrier barrier = new CyclicBarrier(5);

    public static void main(String[] args) throws Exception {

        ExecutorService executor = Executors.newCachedThreadPool();

        for (int i = 0; i < 10; i++) {
            final int threadNum = i;
            Thread.sleep(1000);
            executor.execute(() -> {
                try {
                    race(threadNum);
                } catch (Exception e) {
                    System.out.println(e);
                }
            });
        }
        executor.shutdown();
    }

    private static void race(int threadNum) throws Exception {

        Thread.sleep(1000);
        System.out.println("is ready:"+ threadNum);
        barrier.await();     //等待方法
        System.out.println("continue:"+threadNum);
    }
}

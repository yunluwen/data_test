package com.zyh.house_scala.concurrent.aqs;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * AQS组件：CountDownLatch
 *
 * 同步辅组类，完成阻塞当前线程的功能，
 * 给定了一个计数器，原子操作，计数器不能重置。
 *
 * 调用await()方法会使线程处于阻塞状态，
 * 直到其他线程调用CountDown()方法时，
 * 才继续执行
 *
 * 当计数器变为0的时候，所有等待的线程才会继续执行
 *
 * 使用场景：查询需要等待某个条件完成后才能继续执行后续操作（Ex:并行计算）拆分任务
 *
 */
public class CountDownLatchExample1 {

    private final static int threadCount=200;
    public static void main(String[] args) throws InterruptedException {
        //定义一个线程池
        ExecutorService exec= Executors.newCachedThreadPool();

        final CountDownLatch countDownLatch=new CountDownLatch(threadCount);
        for(int i=0;i<threadCount;i++){
            final int threadNum=i;
            exec.execute(()->{
                try {
                    test(threadNum);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    countDownLatch.countDown();  //
                }
            });
        }
        countDownLatch.await();  //保证全部的线程都执行完
        System.out.println("finish");
        exec.shutdown();         //关闭线程池
    }
    private static void test(int threadNum) throws InterruptedException {
        Thread.sleep(100);
        System.out.println("threadNum:"+threadNum);
        Thread.sleep(100);
    }
}

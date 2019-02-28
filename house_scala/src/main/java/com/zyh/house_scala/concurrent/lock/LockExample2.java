package com.zyh.house_scala.concurrent.lock;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockExample2 {

    // 请求总数
    public static int clientTotal = 5000;

    // 同时并发执行的线程数
    public static int threadTotal = 200;

    public static int count = 0;

    private final static Lock lock = new ReentrantLock();

    public static void main(String[] args) throws Exception {
        ExecutorService executorService = Executors.newCachedThreadPool();
        //信号量
        final Semaphore semaphore = new Semaphore(threadTotal);
        //计数器
        final CountDownLatch countDownLatch = new CountDownLatch(clientTotal);
        for (int i = 0; i < clientTotal ; i++) {
            executorService.execute(() -> {
                try {
                    semaphore.acquire();  //获得一个信号量
                    add();
                    semaphore.release();  //释放一个信号量
                } catch (Exception e) {
                    System.out.println("exception:"+e);
                }
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();     //等待
        executorService.shutdown();
        System.out.println("count:"+count);
    }

    private synchronized static void add() {
        lock.lock();        //获得锁
        try {
            count++;
        } finally {
            lock.unlock();  //释放锁
        }
    }
}
package com.zyh.house_scala.concurrent.controller;

import com.zyh.house_scala.concurrent.annotations.NotThreadSafe;
import com.zyh.house_scala.concurrent.annotations.ThreadSafe;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

//线程安全
@ThreadSafe
public class ConcurrencyTestAtomic {

    public static int clientTotal = 5000;

    public static int threadTotal = 200;

    public static AtomicInteger count = new AtomicInteger(0);

    public static void main(String[] args) throws Exception {
        //创建一个线程池
        ExecutorService executorService = Executors.newCachedThreadPool();
        //信号量
        final Semaphore semaphore = new Semaphore(threadTotal);
        final CountDownLatch countDownLatch = new CountDownLatch(clientTotal);
        for(int i=0;i < clientTotal;i++){
            executorService.execute(() -> {
                try {
                    semaphore.acquire();
                    add();
                    semaphore.release();   //释放进程
                }catch (Exception e){
                    e.printStackTrace();
                }
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        executorService.shutdown();
        System.out.println("count:"+count.get());
    }

    private static void add(){
        /**
         * compareAndSwap
         * cas算法的核心：
         * 拿当前的值和底层的值进行比较，如果相同，再执行对应的操作
         * 如果不相同，就一直执行循环，直到相同
         */
        count.incrementAndGet();
//        count.getAndIncrement();
    }
}

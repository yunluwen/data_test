package com.zyh.house_scala.concurrent.controller;

import com.zyh.house_scala.concurrent.annotations.NotThreadSafe;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

@NotThreadSafe    //线程不安全的写法
public class ConcurrencyTest {

    public static int clientTotal = 5000;

    public static int threadTotal = 200;

    public static volatile int count = 0;   //不是线程安全的，不具有原子性，不适合计数操作

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
        System.out.println("count:"+count);
    }

    private static void add(){
        count++;
    }
    /**
     * 使用synchronized修饰静态方法之后，就变成线程安全的了
     */
    //private static synchronized void add(){
//        count++;
//    }
}

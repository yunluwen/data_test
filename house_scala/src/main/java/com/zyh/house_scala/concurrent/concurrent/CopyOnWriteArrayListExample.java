package com.zyh.house_scala.concurrent.concurrent;

import com.zyh.house_scala.concurrent.annotations.ThreadSafe;

import java.util.List;
import java.util.concurrent.*;

/**
 * 并发容器
 * CopyOnWriteArrayList
 */
@ThreadSafe
public class CopyOnWriteArrayListExample {

    //请求总和
    public static int clientTotal=5000;
    //同时并发执行线程数
    public static int threadTotal=200;

    private static List<Integer> list=new CopyOnWriteArrayList<Integer>();

    public static void main(String[] args) throws Exception{
        ExecutorService executorService=Executors.newCachedThreadPool();
        final Semaphore semaphore=new Semaphore(threadTotal);
        final CountDownLatch countDownLatch=new CountDownLatch(clientTotal);
        for(int i=0;i<clientTotal;i++) {
            final int count=i;
            executorService.execute(()->{
                try {
                    semaphore.acquire();
                    update(count);
                    semaphore.release();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        executorService.shutdown();
        System.out.println("size:"+list.size());
    }
    private static void update(int i) {
        list.add(i);
    }
}

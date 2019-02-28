package com.zyh.house_scala.concurrent.syncContainer;

import com.zyh.house_scala.concurrent.annotations.ThreadSafe;

import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;


@ThreadSafe
public class HashTableExample {

    public static int clientTotal = 5000;

    public static int threadTotal = 200;

    //HashTable是线程安全的
    private static Map<Integer,Integer> map = new Hashtable<>();

    public static void main(String[] args) throws Exception {
        //创建一个线程池
        ExecutorService executorService = Executors.newCachedThreadPool();
        //信号量
        final Semaphore semaphore = new Semaphore(threadTotal);
        final CountDownLatch countDownLatch = new CountDownLatch(clientTotal);
        for(int i=0;i < clientTotal;i++){
            final int count = i;
            executorService.execute(() -> {
                try {
                    semaphore.acquire();
                    update(count);
                    semaphore.release();   //释放进程
                }catch (Exception e){
                    e.printStackTrace();
                }
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        executorService.shutdown();

        System.out.println("map size:"+map.size());
    }

    private static void update(int count){
        map.put(count,1);
    }
}

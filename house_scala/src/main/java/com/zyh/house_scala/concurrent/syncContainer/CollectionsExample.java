package com.zyh.house_scala.concurrent.syncContainer;

import com.google.common.collect.Lists;
import com.zyh.house_scala.concurrent.annotations.NotThreadSafe;
import com.zyh.house_scala.concurrent.annotations.ThreadSafe;

import java.util.Collections;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * 线程安全的
 * Collections.synchronizedXXX
 */
@ThreadSafe
public class CollectionsExample {

    public static int clientTotal = 5000;

    public static int threadTotal = 200;

    //线程安全的 Collections.synchronizedXXX
    private static List<Integer> vector = Collections.synchronizedList(Lists.newArrayList());

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
                    update();
                    semaphore.release();   //释放进程
                }catch (Exception e){
                    e.printStackTrace();
                }
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        executorService.shutdown();

        System.out.println("vector size:"+vector.size());
    }

    private static void update(){
        vector.add(1);
    }
}

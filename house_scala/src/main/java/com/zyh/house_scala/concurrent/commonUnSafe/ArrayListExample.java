package com.zyh.house_scala.concurrent.commonUnSafe;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class ArrayListExample {

    public static int clientTotal = 5000;

    public static int threadTotal = 200;

    //线程不安全
    private static List<Integer> list = new ArrayList<>();

    //线程不安全
    private static Set<Integer> set = new HashSet<>();



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
//        System.out.println("list size:"+list.size());
        System.out.println("set size:"+set.size());
    }

    private static void update(){
//        list.add(1);
        set.add(1);
    }

}

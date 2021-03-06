package com.zyh.house_scala.concurrent.commonUnSafe;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class StringExample {

    public static int clientTotal = 5000;

    public static int threadTotal = 200;

    //线程不安全的
    //在方法内部，由于堆栈封闭，线程安全，优先选择StringBuilder
    public static StringBuilder builder = new StringBuilder();

    //线程安全的,实现方法加了synchronized关键字，性能比较低
    public static StringBuffer buffer = new StringBuffer();

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
//        System.out.println("stringBuilder length:"+builder.length());
        System.out.println("stringBuilder length:"+buffer.length());
    }

    private static void update(){
//        builder.append(1);
        buffer.append(1);
    }
}

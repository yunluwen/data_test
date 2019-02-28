package com.zyh.house_scala.concurrent.aqs;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * 信号量
 * 并发访问控制线程个数(同步机制)，   
 * 提供了两个方法，实现有限大小的链表大小
 * semaphore.acquire(); // 获取一个许可
 * semaphore.release(); // 释放一个许可
 * semaphore.acquire(n);//获取多个许可
 * semaphore.release(n); // 释放n个许可
 *
 * 使用场景：仅能提供有限访问的资源，比如数据库连接数
 *
 * tryAcquire())//尝试获取一个许可
 *
 * tryAcquire 四个带参方法 
 * 1 tryAcquire(long timeout, TimeUnit unit) 
 * 2 tryAcquire()
 * 3 tryAcquire(int permits)
 * 4 boolean tryAcquire(int permits, long timeout, TimeUnit unit)
 */
public class SemaphoreExample4 {

    private final static int threadCount = 20;

    public static void main(String[] args) throws Exception {

        ExecutorService exec = Executors.newCachedThreadPool();

        //信号量，3个许可
        final Semaphore semaphore = new Semaphore(3);

        for (int i = 0; i < threadCount; i++) {
            final int threadNum = i;
            exec.execute(() -> {
                try {
                    //执行5s之内的
                    if (semaphore.tryAcquire(5000, TimeUnit.MILLISECONDS)) { // 尝试获取一个许可
                        test(threadNum);
                        semaphore.release(); // 释放一个许可
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
        exec.shutdown();
    }

    private static void test(int threadNum) throws Exception {
        System.out.println(threadNum);
        Thread.sleep(1000);
    }
}
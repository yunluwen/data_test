package com.zyh.house_scala.concurrent.lock;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * ReentrantLock 与锁java 
 * 两类锁：
 * 1 synchronized  (不会引起死锁)                      
 * 2 JUC的 ReentrantLock  (可能会引起死锁)
 *
 * ReentrantLock 与synchronized 的区别
 * 1 &&&  可重入性 两者都是可重入锁 ，同一线程进入一次 锁的计数器就自增1 ，锁的计数器下降为0 时才释放锁
 * 2 &&&  synchronized 是依赖jvm实现的（操作系统实现，难查源码），ReentrantLock 是依赖jdk实现的（用户实现）
 * 3 &&&  两者性能差不多 ，推荐使用synchronized ，synchronized 优化借鉴了CAS技术，
 *    用户态解决加锁问题避免进入内核态 使线程阻塞
 * 4 &&&  synchronized  更方便它是编译器保证锁的加锁和释放的，ReentrantLock 手工释放和加锁，
 *   在finally释放锁锁的细腻度和灵活度 ReentrantLock 更好 
 *
 * ReentrantLock  的独有的功能
 * 1  ReentrantLock 可指定是公平锁和非公平锁  synchronized 只能是非公平锁
 *    公平锁（先等待的线程先获得锁）
 * 2 提供了一个Condition类，可以分组唤醒需要唤醒的线程synchronized  唤醒一个要不全部唤醒
 * 3 提供能够中断等待锁的线程的机制，lock.lockInterruptibly()
 *
 * ReentrantLock 实现是一种自旋锁，通过循环调用cas操作自加操作，避免了线程进入内核态发生阻塞 
 * synchronized 不会忘记释放锁
 *
 * ReentrantLock 函数方法
 * tryLock()：仅在调用时锁定未被另外一个线程保持的情况下获取锁定
 * tryLock(long timeout, TimeUnit unit) 如果锁定在给定的时间内没有被另一个线程保持，且当前线程没有被中断，则获取这个锁定
 * lockInterruptibly() 当前线程如果没有中断就获取锁定，如果已经中断就抛出异常
 * isLocked() 查询当前此锁定是否由任意线程保持，
 *
 * 总结:
 * 1 只有少量竞争者的时候，synchronized是比较好的选择
 * 2 竞争者不少，线程的数量可以预估的，ReentrantLock 是一个比较好的锁实现
 */
public class LockExample1 {

    // 请求总数
    public static int clientTotal = 5000;

    // 同时并发执行的线程数
    public static int threadTotal = 200;

    public static int count = 0;

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
        count++;
    }
}
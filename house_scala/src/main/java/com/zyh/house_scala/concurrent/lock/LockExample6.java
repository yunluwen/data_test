package com.zyh.house_scala.concurrent.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Condition的使用
 * 也是一个多线程协调工作的工具类
 */
public class LockExample6 {

    public static void main(String[] args) {
        ReentrantLock reentrantLock = new ReentrantLock();
        Condition condition = reentrantLock.newCondition();

        new Thread(() -> {
            try {
                reentrantLock.lock(); // 线程加入到AQS的等待队列中
                System.out.println("wait signal"); // 1
                condition.await();   //调用await()方法后 线程从AQS对列中溢出（锁的释放），进入到condition的等待队列中
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("get signal"); // 4
            reentrantLock.unlock();
        }).start();

        new Thread(() -> {
            reentrantLock.lock();
            System.out.println("get lock"); // 2
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            condition.signalAll();
            System.out.println("send signal ~ "); // 3
            reentrantLock.unlock();
        }).start();
    }
}

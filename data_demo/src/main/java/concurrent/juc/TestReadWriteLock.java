package concurrent.juc;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * ReadWriteLock : 读写锁
 * 写写/读写   需要"互斥"
 * 读读   不需要互斥
 */
public class TestReadWriteLock {

    public static void main(String[] args) {
        ReadWriteLockDemo demo = new ReadWriteLockDemo();

        new Thread(new Runnable() {
            @Override
            public void run() {
                demo.get();
            }
        },"Read").start();

        for(int i=0;i<100;i++){
            final int j = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    demo.set(j);
                }
            },"Write").start();
        }
    }

}

class ReadWriteLockDemo {

    private int number = 0;

    //定义一个读写锁实例
    private ReadWriteLock lock = new ReentrantReadWriteLock();

    //读
    public void get(){
        lock.readLock().lock();   //上读锁
        try {
            System.out.println(Thread.currentThread().getName()+":"+number);
        }finally {
            lock.readLock().unlock(); //释放锁
        }
    }

    //写
    public void set(int number){
        lock.writeLock().lock();  //上写锁

        try {
            System.out.println(Thread.currentThread().getName());
            this.number = number;
        }finally {
            lock.writeLock().unlock();  //释放锁
        }
    }

}


package concurrent.juc;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 1、用于解决多线程安全问题的方式
 * synchronized ： 隐式锁
 * 1、同步代码块
 * 2、同步方法
 *
 * jdk 1.5后：
 * 3、同步锁Lock
 * 注意：是一个显示锁，需要通过Lock()方法上锁，必须通过unlock()【通常放到finally代码块里】方法进行释放锁
 *
 */
public class TestLock {

    public static void main(String[] args) {
        Ticket ticket = new Ticket();

        new Thread(ticket,"1").start();
        new Thread(ticket,"2").start();
        new Thread(ticket,"3").start();
    }
}

class Ticket implements Runnable{

    private int tick = 100;

    private Lock lock = new ReentrantLock();

    @Override
    public void run() {

        while(true){

            lock.lock();         //上锁
            try {
                if (tick > 0) {
                    try {
                        Thread.sleep(200);
                        System.out.println(Thread.currentThread().getId() + "\t完成售票，余票：" + --tick);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }finally {
                lock.unlock();   //释放锁
            }

        }
    }
}



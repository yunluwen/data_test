package concurrent.juc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 线程按序交替执行
 *
 * 编写一个程序，开启3个线程，这三个线程的Id分别是A,B,C,每个线程将自己的Id在屏幕上打印10遍，
 * 按ABCABCABC....的顺序
 */
public class TestABCAlternate {

    public static void main(String[] args) {
        Alternate alternate = new Alternate();

        new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i=1;i<=20;i++){
                    alternate.LoopA(i);
                }
            }
        },"A").start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i=1;i<=20;i++){
                    alternate.LoopB(i);
                }
            }
        },"B").start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i=1;i<=20;i++){
                    alternate.LoopC(i);
                    System.out.println("------------------------");
                }
            }
        },"C").start();
    }
}

class Alternate {

    private int number = 1;//当前正在执行线程的标记

    private Lock lock = new ReentrantLock();

    private Condition condition1 = lock.newCondition();
    private Condition condition2 = lock.newCondition();
    private Condition condition3 = lock.newCondition();

    /**
     *
     * @param totalLoop 循环第几轮
     */
    public void LoopA(int totalLoop){
        lock.lock();

        try {
            //1、判断
            if(number != 1){
                condition1.await();
            }

            //2、打印多少遍
            for(int i=1;i<=5;i++){
                System.out.println(Thread.currentThread().getName()+"\t"+i+"\t"+totalLoop);
            }

            //3、唤醒
            number = 2;
            condition2.signal();
        }catch (Exception e){
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void LoopB(int totalLoop){
        lock.lock();

        try {
            //1、判断
            if(number != 2){
                condition2.await();
            }

            //2、打印
            for(int i=1;i<=5;i++){
                System.out.println(Thread.currentThread().getName()+"\t"+i+"\t"+totalLoop);
            }

            //3、唤醒
            number = 3;
            condition3.signal();
        }catch (Exception e){
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void LoopC(int totalLoop){
        lock.lock();

        try {
            //1、判断
            if(number != 3){
                condition3.await();
            }

            //2、打印
            for(int i=1;i<=5;i++){
                System.out.println(Thread.currentThread().getName()+"\t"+i+"\t"+totalLoop);
            }

            //3、唤醒
            number = 1;
            condition1.signal();
        }catch (Exception e){
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

}


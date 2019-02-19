package concurrent.juc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 生产者消费者案例
 *
 * 等待唤醒机制
 * *
 * 同步锁实现的等待唤醒机制
 */
public class TestProductorAndConsumerForLock {

    public static void main(String[] args) {
        ClerkDemo clerk = new ClerkDemo();

        ProductorDemo productor = new ProductorDemo(clerk);
        ConsumerDemo consumer = new ConsumerDemo(clerk);

        new Thread(productor,"生产者A").start();
        new Thread(consumer,"消费者B").start();

        new Thread(productor,"生产者C").start();
        new Thread(consumer,"消费者D").start();
    }
}


//店家
class ClerkDemo {

    private int product = 0;

    private Lock lock = new ReentrantLock();

    private Condition condition = lock.newCondition();

    //进货
    public void get(){

        lock.lock();
        try {
            while(product >= 1){                 //为了避免虚假唤醒的问题，应该总是使用在循环中
                System.out.println("产品已经满");
                try {
                    condition.await();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            System.out.println(Thread.currentThread().getId()+":当前货物量-"+ ++product);
            condition.signalAll();   //唤醒wait等待的线程
        }finally {
            lock.unlock();
        }

    }

    //卖货
    public synchronized void sale() {

        lock.lock();

        try {
            while (product <= 0) {
                System.out.println("缺货");
                try {
                    //消费者等待，防止消费时持续消费
                    condition.await();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            System.out.println(Thread.currentThread().getId() + ":当前货物量-" + --product);
            condition.signalAll();   //唤醒所有线程
        } finally {
            lock.unlock();
        }
    }
}

//生产者
class ProductorDemo implements Runnable {

    private ClerkDemo clerk;

    public ProductorDemo(ClerkDemo clerk) {
        this.clerk = clerk;
    }

    @Override
    public void run() {
        for(int i=0;i<20;i++){
            clerk.get();
        }
    }
}

//消费者
class ConsumerDemo implements Runnable {

    private ClerkDemo clerk;

    public ConsumerDemo(ClerkDemo clerk) {
        this.clerk = clerk;
    }

    @Override
    public void run() {
        for(int i=0;i<20;i++){
            clerk.sale();
        }
    }
}
























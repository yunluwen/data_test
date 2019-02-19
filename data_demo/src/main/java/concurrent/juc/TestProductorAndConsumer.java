package concurrent.juc;

/**
 * 生产者消费者案例
 *
 * 等待唤醒机制
 * wait()
 * notify()
 * notifyAll()
 */
public class TestProductorAndConsumer {

    public static void main(String[] args) {
        Clerk clerk = new Clerk();

        Productor productor = new Productor(clerk);
        Consumer consumer = new Consumer(clerk);

        new Thread(productor,"生产者A").start();
        new Thread(consumer,"消费者B").start();

        new Thread(productor,"生产者C").start();
        new Thread(consumer,"消费者D").start();
    }
}


//店家
class Clerk {

    private int product = 0;

    //进货
    public synchronized void get(){
        while(product >= 1){                 //为了避免虚假唤醒的问题，应该总是使用在循环中
            System.out.println("产品已经满");
            try {
                //生产者等待，防止满了的时候持续生产
                //wait和sleep的区别
                //wait释放锁，sleep不释放锁
                this.wait();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        System.out.println(Thread.currentThread().getId()+":当前货物量-"+ ++product);
        this.notifyAll();   //唤醒wait等待的线程
    }

    //卖货
    public synchronized void sale(){
        while(product <= 0){
            System.out.println("缺货");
            try {
                //消费者等待，防止消费时持续消费
                this.wait();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        System.out.println(Thread.currentThread().getId()+":当前货物量-"+ --product);
        this.notifyAll();   //唤醒所有线程
    }
}

//生产者
class Productor implements Runnable {

    private Clerk clerk;

    public Productor(Clerk clerk) {
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
class Consumer implements Runnable {

    private Clerk clerk;

    public Consumer(Clerk clerk) {
        this.clerk = clerk;
    }

    @Override
    public void run() {
        for(int i=0;i<20;i++){
            clerk.sale();
        }
    }
}
























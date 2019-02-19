package concurrent.juc;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 原子性操作
 * i++ 涉及 "读-改-写" 的操作
 *
 * 原子变量：jdk1.5 后 java.util.concurrent.atomic包
 *       1、volatile保证内存可见性
 *       2、CAS (Compare-And-Swap) 算法保证数据的原子性
 *          CAS算法是硬件对于并发操作共享数据的支持
 *          CAS 包含了三个操作数：
 *              内存值V,预估值A,更新值B
 *              当且仅当V==A时，V = B(赋值),否则将不做任何操作
 */
public class TestAtomicDemo {

    public static void main(String[] args) {
        AtomicDemo atomicDemo = new AtomicDemo();
        for(int i=0;i<10;i++){
            new Thread(atomicDemo).start();
        }
    }

}

class AtomicDemo implements Runnable {

    private AtomicInteger serialNumber = new AtomicInteger(0);

    @Override
    public void run() {
        try {
            Thread.sleep(200);
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("Thread-"+Thread.currentThread().getId()+"\t"+getSerialNumber());
    }

    public int getSerialNumber() {
        return serialNumber.getAndIncrement();  //获取并递增方法
        //serialNumber.getAndDecrement();     //获取并递减方法
//        return serialNumber.compareAndSet()
    }
}
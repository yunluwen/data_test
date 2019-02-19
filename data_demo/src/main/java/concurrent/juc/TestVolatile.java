package concurrent.juc;

/**
 * 内存可见性问题是多个线程操作一个资源，彼此不可见的问题
 * volatile 关键字：当多个线程操作共享数据时，可以保证内存中的数据可见
 * 相较于 synchronized 是一种轻量级的同步策略
 *
 * 注意：
 * 1、volatile 不具备互斥性
 * 2、volatile 不能保证变量的原子性
 */
public class TestVolatile {

    //main线程
    public static void main(String[] args) {

        //线程1
        ThreadDemo threadDemo = new ThreadDemo();
        //new Thread(threadDemo).run();    //线性执行
        new Thread(threadDemo).start();

        while(true){
            if(threadDemo.isFlag()){
                System.out.println("lalala....");
                break;
            }
        }
    }

}

class ThreadDemo implements Runnable {
    //共享数据，存在主存
    private volatile boolean flag = false;

    @Override
    public void run() {
        try {
            Thread.sleep(2000);
        }catch (Exception e){
            e.printStackTrace();
        }
        flag = true;
        System.out.println(flag);
    }

    boolean isFlag() {
        return flag;
    }

}

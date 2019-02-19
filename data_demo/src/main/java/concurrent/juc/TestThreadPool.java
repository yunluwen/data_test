package concurrent.juc;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 创建线程实例的方法4
 * 线程池
 * 1、线程池提供了一个线程队列，队列中保存着所有等待状态的线程。避免了创建与销毁的额外开销，提高了响应的速度
 * 2、线程池的体系结构：
 *   java.util.concurrent.Executor : 负责线程的使用和调度的根接口
 *   |--ExecutorService : 子接口，线程池的主要接口
 *      |--ThreadPoolExecutor : 实现类
 *      |--ScheduledExecutorService 子接口 ： 负责线程的调度
 *          |--ScheduledThreadPoolExecutor : 继承hreadPoolExecutor 实现ScheduledExecutorService(既有线程池的功能，又可以调度)
 *
 * 3、工具类：Executors
 *  ExecutorService newFixedThreadPool() : 创建固定大小的线程池
 *  ExecutorService newCachedThreadPool() : 缓存线程池，线程池的数量不固定，可以根据需求自动的更改数量
 *  ExecutorService newSigleThreadExecutor() : 创建单个线程池，线程池中只有一个线程
 *
 *  ScheduledExecutorService newScheduledThreadPool() : 创建固定大小的线程，可以延迟或定时的执行任务
 */
public class TestThreadPool {

    public static void main(String[] args) {
        //1、创建一个线程池,5个线程
        ExecutorService executorService = Executors.newFixedThreadPool(5);

        //2、为线程池中的线程分配任务
        for(int i = 0;i<10;i++) {
            executorService.execute(() -> {
                System.out.println(Thread.currentThread().getId());
            });
        }

        //3、关闭线程池
        executorService.shutdown();   //等待线程池中的任务执行完，再关闭
//        executorService.shutdownNow();  //不管任务是否执行完，立即关闭
    }
}





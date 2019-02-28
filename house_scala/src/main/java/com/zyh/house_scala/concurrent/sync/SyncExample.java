package com.zyh.house_scala.concurrent.sync;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * sync测试
 * synchronized不属于方法修饰的一部分
 */
public class SyncExample {

    //修饰代码块,作用于调用的对象,当前对象，多个对象调用是互相不影响的
    public void test1(){
        synchronized (this){
            for(int i=0;i<10;i++){
                System.out.println("test1------"+i);
            }
        }
    }

    //修饰一个方法，作用于调用的对象，当前对象，多个对象调用是互相不影响的
    public synchronized void test2(int j){
        for(int i=0;i<10;i++){
            System.out.println("test2-----"+i+"   j:"+j);
        }
    }

    public static void main(String[] args) {
        SyncExample syncExample = new SyncExample();
        SyncExample syncExample1 = new SyncExample();
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(() -> {
            syncExample.test2(1);
        });

//        executorService.execute(() -> {
//            syncExample.test2();
//        });

        executorService.execute(() -> {
            syncExample1.test2(2);
        });
    }

}

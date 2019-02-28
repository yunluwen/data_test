package com.zyh.house_scala.concurrent.sync;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SyncExample2 {

    //修饰一个静态方法,作用于所有的对象
    public static synchronized void test1(){
        for(int i=0;i<10;i++){
            System.out.println("test2-----"+i);
        }
    }

    //修饰一个类，作用于多有的对象
    public void test2(){
        synchronized (SyncExample2.class){
            for(int i=0;i<10;i++){
                System.out.println("test1------"+i);
            }
        }
    }

    public static void main(String[] args) {
        SyncExample2 syncExample = new SyncExample2();
        SyncExample2 syncExample1 = new SyncExample2();
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(() -> {
            syncExample.test1();
        });

//        executorService.execute(() -> {
//            syncExample1.test1();
//        });
    }
}

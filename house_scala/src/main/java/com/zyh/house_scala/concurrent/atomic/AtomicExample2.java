package com.zyh.house_scala.concurrent.atomic;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

public class AtomicExample2 {

    //第一个参数是对应的类，第二个参数是对应的字段名称
    /**
     * 原子性更新某个类的某一个字段的值
     */
    private static AtomicIntegerFieldUpdater atomicIntegerFieldUpdater =
            AtomicIntegerFieldUpdater.newUpdater(AtomicExample2.class,"count");

    //必须用volatile，非static
    public volatile int count = 100;

    private static AtomicExample2 atomicExample2 = new AtomicExample2();

    public static void main(String[] args) {
        if(atomicIntegerFieldUpdater.compareAndSet(atomicExample2,100,120)){
            System.out.println("update success,"+atomicExample2.count);
        }
    }
}

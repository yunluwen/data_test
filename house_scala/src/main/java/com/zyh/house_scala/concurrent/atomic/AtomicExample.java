package com.zyh.house_scala.concurrent.atomic;

import com.zyh.house_scala.concurrent.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicReference;

@ThreadSafe
public class AtomicExample {

    private static AtomicReference<Integer> count = new AtomicReference<>(0);

    public static void main(String[] args) {
        count.compareAndSet(0,2);
        count.compareAndSet(0,1);
        count.compareAndSet(2,4);
        count.compareAndSet(1,3);
        System.out.println(count);
    }
}

package com.zyh.house_scala.concurrent.syncContainer;

import com.zyh.house_scala.concurrent.annotations.NotThreadSafe;

import java.util.Vector;

/**
 * vector是线程安全的
 * 但是也有线程不安全的时候
 */

@NotThreadSafe
public class VectorExample {

    private static Vector<Integer> vector = new Vector<>();

    public static void main(String[] args) {
        for(int i=0;i<10;i++){
            //添加元素
            vector.add(i);
        }

        Thread thread = new Thread(){
            @Override
            public void run() {
                for(int i=0;i<10;i++){
                    //移除元素
                    vector.remove(i);
                }
            }
        };

        Thread thread2 = new Thread(){
            @Override
            public void run() {
                for(int i=0;i<10;i++){
                    vector.add(i);
                }
            }
        };

        thread.start();
        thread2.start();
    }
}

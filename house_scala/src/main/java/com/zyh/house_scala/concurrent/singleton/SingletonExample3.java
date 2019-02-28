package com.zyh.house_scala.concurrent.singleton;

//懒汉模式：单例实例在第一次使用的时候创建
//在单线程下面没问题，多线程会出现问题

//双重同步锁，线程不安全，可能指令重排，导致线程不安全的问题
//使用volatile标示单例实例，来禁止指令重排，就变成线程安全的了
import com.zyh.house_scala.concurrent.annotations.ThreadSafe;

@ThreadSafe
public class SingletonExample3 {

    //私有构造函数
    private SingletonExample3(){
    }

    //正常的执行顺序
    //1、分配对象内存空间
    //2、初始化对象
    //3、设置单例实例指向刚分配的内存

    //JVM和cpu优化，发生了指令重排
    //1、分配对象内存空间
    //3、设置单例实例指向刚分配的内存
    //2、初始化对象


    //单例对象 volatile + 双重检测机制 -> 禁止指令重排
    private static volatile SingletonExample3 singletonExample = null;

    //静态工厂方法
    public static SingletonExample3 getSingletonExample(){
        if(singletonExample == null){                 //双重检测机制
            synchronized (SingletonExample3.class) {  //同步锁
                if(singletonExample == null) {
                    singletonExample = new SingletonExample3();
                }
            }
        }
        return singletonExample;
    }
}

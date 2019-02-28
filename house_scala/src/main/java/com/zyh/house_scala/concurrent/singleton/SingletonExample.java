package com.zyh.house_scala.concurrent.singleton;

//懒汉模式：单例实例在第一次使用的时候创建
//在单线程下面没问题，多线程会出现问题

import com.zyh.house_scala.concurrent.annotations.NotThreadSafe;

@NotThreadSafe
public class SingletonExample {

    //私有构造函数
    private SingletonExample(){
    }
    //单例对象
    private static SingletonExample singletonExample = null;

    //静态工厂方法,增加synchronized就变成线程安全了，不推荐
    public static synchronized SingletonExample getSingletonExample(){
        if(singletonExample == null){
            singletonExample = new SingletonExample();
        }
        return singletonExample;
    }
}

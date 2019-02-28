package com.zyh.house_scala.concurrent.singleton;

//饿汉式：单例实例在类装载使用时进行创建

import com.zyh.house_scala.concurrent.annotations.ThreadSafe;

@ThreadSafe
public class SingletonExample2 {

    //私有构造函数
    private SingletonExample2(){
    }
    //单例对象
    private static SingletonExample2 singletonExample2 = new SingletonExample2();

    //静态工厂方法
    public static SingletonExample2 getSingletonExample(){
        return singletonExample2;
    }
}

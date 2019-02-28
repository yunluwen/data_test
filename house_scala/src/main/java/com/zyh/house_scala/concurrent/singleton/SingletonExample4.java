package com.zyh.house_scala.concurrent.singleton;

//饿汉式：单例实例在类装载使用时进行创建

import com.zyh.house_scala.concurrent.annotations.ThreadSafe;

@ThreadSafe
public class SingletonExample4 {

    //私有构造函数
    private SingletonExample4(){
    }
    //单例对象
    private static SingletonExample4 singletonExample2 = null;

    static {
        singletonExample2 = new SingletonExample4();
    }

    //静态工厂方法
    public static SingletonExample4 getSingletonExample(){
        return singletonExample2;
    }

    public static void main(String[] args) {
        System.out.println(getSingletonExample().hashCode());
        System.out.println(getSingletonExample().hashCode());
    }
}

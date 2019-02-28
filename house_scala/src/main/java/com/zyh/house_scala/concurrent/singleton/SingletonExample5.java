package com.zyh.house_scala.concurrent.singleton;

//枚举模式定义的单例模式

import com.zyh.house_scala.concurrent.annotations.ThreadSafe;

//线程安全，推荐使用，最安全
@ThreadSafe
public class SingletonExample5 {

    public SingletonExample5() {
    }

    public static SingletonExample5 getInstance(){
        return Singleton.INSTANCE.getInstance();
    }

    public enum Singleton {
        INSTANCE;

        private SingletonExample5 singletonExample5;

        //JVM保证这个方法绝对只调用一次
        Singleton() {
            singletonExample5 = new SingletonExample5();
        }

        public SingletonExample5 getInstance(){
            return singletonExample5;
        }
    }

    public static void main(String[] args) {
        System.out.println(getInstance().hashCode());
        System.out.println(getInstance().hashCode());
    }

}

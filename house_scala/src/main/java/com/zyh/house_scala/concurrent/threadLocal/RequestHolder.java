package com.zyh.house_scala.concurrent.threadLocal;

//ThreadLocal的使用
//ThreadLocal线程封闭 : 特别好的封闭方法
public class RequestHolder {

    private final static ThreadLocal<Long> requestHolder = new ThreadLocal<>();

    public static void add(Long id) {
        requestHolder.set(id);
    }

    public static Long getId(){
        return requestHolder.get();
    }

    //移除信息
    public static void remove(){
        requestHolder.remove();
    }
}

package com.zyh.house_scala.concurrent.immutable;

import com.google.common.collect.Maps;

import java.util.Map;

public class ImumutableExample {

    private final static Integer a = 1;
    private final static String b = "lalala";
    private final static Map<Integer,Integer> map = Maps.newHashMap();

    static {
        map.put(1,2);
        map.put(2,3);
        map.put(3,4);
    }

    public static void main(String[] args) {
//        b = "haha";  //错误
        //引用类型初始化之后不能再指向另外一个对象，但是是可以修改的
        map.put(1,9);
        System.out.println(map.get(1));
    }
}

package com.zyh.house_scala.concurrent.immutable;

import com.google.common.collect.Maps;
import com.zyh.house_scala.concurrent.annotations.ThreadSafe;

import java.util.Collections;
import java.util.Map;

@ThreadSafe
public class ImumutableExample2 {

    private static Map<Integer,Integer> map = Maps.newHashMap();

    static {
        map.put(1,2);
        map.put(2,3);
        map.put(3,4);
        //增加了这段代码之后，map里面的值就不允许修改了
        map = Collections.unmodifiableMap(map);
    }

    public static void main(String[] args) {
        //这个时候，再修改，就出现异常
        map.put(1,9);
        System.out.println(map.get(1));
    }
}

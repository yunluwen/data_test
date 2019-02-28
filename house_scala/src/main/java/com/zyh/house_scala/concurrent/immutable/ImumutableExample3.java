package com.zyh.house_scala.concurrent.immutable;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.zyh.house_scala.concurrent.annotations.ThreadSafe;
import scala.Int;

import java.util.Collections;
import java.util.Map;

/**
 * 不可变对象是为了躲避并发问题
 */
@ThreadSafe
public class ImumutableExample3 {

    //不可变的List
    private final static ImmutableList list = ImmutableList.of(1,2,3);

    private final static ImmutableSet set = ImmutableSet.of(list);

    private final static ImmutableMap<Integer,Integer> map = ImmutableMap.of(1,2,3,4);

    private final static ImmutableMap<Integer,Integer> map2 = ImmutableMap.<Integer,Integer>builder()
            .put(1,2)
            .put(3,4)
            .put(5,6)
            .put(7,8)
            .put(9,0).build();

    public static void main(String[] args) {
        //如果修改，就会出现 java.lang.UnsupportedOperationException
//        list.add(45);
//        set.add(789);
//        map2.put(333,666);
        System.out.println(map.get(1));   //2
        System.out.println(map2.get(3));  //4
    }
}

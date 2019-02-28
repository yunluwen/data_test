package com.zyh.house_scala.concurrent.syncContainer;

import com.zyh.house_scala.concurrent.annotations.ThreadSafe;

import java.util.Iterator;
import java.util.Vector;

/**
 * 同步容器出现线程不安全的现象
 * 不支持边foreach 或 迭代 边删除元素，其他同步容器和基础容器也是一样
 *
 * 注意：在foreach或迭代器遍历的过程中不要做删除操作，应该先标记，然后最后再统一删除
 *
 * @author guoyansi 下面例子是边remove边get,造成索引越界
 */
@ThreadSafe
public class VectorExample3 {


    /**
     * 异常
     * java.util.ConcurrentModificationException
     * @param vector
     */
    private static void test1(Vector<Integer> vector) {   //foreach
        for (Integer v : vector) {
            if (v.equals(3)) {
                vector.remove(v);
            }

        }
    }
    /**
     * 异常
     * java.util.ConcurrentModificationException
     * @param vector
     */
    private static void test2(Vector<Integer> vector) {   //迭代器
        Iterator<Integer> iterator = vector.iterator();
        while (iterator.hasNext()) {
            Integer i = iterator.next();
            if (i.equals(3)) {
                vector.remove(i);
            }

        }
    }
    /**
     * 没有异常
     * @param vector
     */
    private static void test3(Vector<Integer> vector) {
        for (int i = 0; i < vector.size(); i++) {
            if (vector.get(i).equals(3)) {
                vector.remove(i);
            }
        }
    }

    public static void main(String[] args) throws Exception {
        Vector<Integer> vector = new Vector<Integer>();
        vector.add(1);
        vector.add(2);
        vector.add(3);
        test3(vector);
    }
}

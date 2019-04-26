package com.zyh.batch;


import org.apache.flink.api.common.functions.GroupReduceFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.table.expressions.In;
import org.apache.flink.util.Collector;

import java.util.Iterator;

/**
 * reduceGroup算子：
 * 对每一组的元素分别进行合并操作。与reduce类似，不过它能为每一组产生一个结果。
 * 如果没有分组，就当作一个分组，此时和reduce一样，只会产生一个结果。
 */
public class GroupReduceFunction001java {

    public static void main(String[] args) throws Exception{
        // 1.设置运行环境，准备运行的数据
        final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
        DataSet<Integer> text = env.fromElements(1, 2, 3, 4, 5, 6,7);

        //2.对DataSet的元素进行合并，这里是计算累加和
        DataSet<Integer> sum = text.reduceGroup(new GroupReduceFunction<Integer, Integer>() {
            @Override
            public void reduce(Iterable<Integer> iterable, Collector<Integer> collector) throws Exception {
                int sum = 0;
                Iterator<Integer> iter = iterable.iterator();
                while(iter.hasNext()){
                    sum += iter.next();
                }
                collector.collect(sum);
            }
        });
        sum.print();

        //3.对DataSet的元素进行分组合并，这里是分别计算偶数和奇数的累加和
        DataSet<Tuple2<Integer,Integer>> sum2 = text.reduceGroup(new GroupReduceFunction<Integer, Tuple2<Integer, Integer>>() {
            @Override
            public void reduce(Iterable<Integer> iterable, Collector<Tuple2<Integer, Integer>> collector) throws Exception {
                int sum0 = 0;
                int sum1 = 0;
                Iterator<Integer> integerIterator = iterable.iterator();
                while(integerIterator.hasNext()){
                    Integer node = integerIterator.next();
                    if(node %2 ==0){
                        sum0 += node;
                    }else {
                        sum1 += node;
                    }
                }
            }
        });
        sum2.print();

        //4.对DataSet的元素进行分组合并，这里是对分组后的数据进行合并操作，统计每个人的工资总和
        //（每个分组会合并出一个结果）
        DataSet<Tuple2<String, Integer>> data = env.fromElements(
                new Tuple2<String, Integer>("zhangsan", 1000),
                new Tuple2<String, Integer>("lisi", 1001),
                new Tuple2<String, Integer>("zhangsan", 3000),
                new Tuple2<String, Integer>("lisi", 1002));
        //4.1根据name进行分组
        DataSet<Tuple2<String,Integer>> group = data.groupBy(0).reduceGroup(
                new GroupReduceFunction<Tuple2<String, Integer>, Tuple2<String, Integer>>() {
                    @Override
                    public void reduce(Iterable<Tuple2<String, Integer>> iterable, Collector<Tuple2<String, Integer>> collector) throws Exception {
                        Iterator<Tuple2<String, Integer>> iterator = iterable.iterator();
                        String name = "";
                        Integer salary = 0;
                        while(iterator.hasNext()){
                            Tuple2<String, Integer> tuple2 = iterator.next();
                            name = tuple2.f0;
                            salary += tuple2.f1;
                        }
                        collector.collect(new Tuple2<String, Integer>(name,salary));
                    }
                }
        );
        System.out.println(group.collect());

    }
}

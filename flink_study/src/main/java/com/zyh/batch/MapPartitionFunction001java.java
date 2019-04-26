package com.zyh.batch;

import org.apache.flink.api.common.functions.MapPartitionFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.util.Collector;

import java.util.Iterator;

/**
 * mapPartition算子：
 * 以partition为粒度，对element进行1：n的转化。
 */
public class MapPartitionFunction001java {

    public static void main(String[] args) throws Exception{
        // 1.设置运行环境,准备运行的数据
        final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
        DataSet<String> text = env.fromElements("flink vs spark", "buffer vs shuffer");

        //2.以partition为粒度，进行map操作，计算element个数
        DataSet<Integer> count = text.mapPartition(new MapPartitionFunction<String, Integer>() {
            @Override
            public void mapPartition(Iterable<String> iterable, Collector<Integer> collector) {
                int sum = 0;
                Iterator iterator = iterable.iterator();
                while(iterator.hasNext()){
                    sum += 1;
                }
                collector.collect(sum);
            }
        });
        count.print();

        //3.以partition为粒度，进行map操作，转化element内容
        DataSet<String> text2 = text.mapPartition(new MapPartitionFunction<String, String>() {
            @Override
            public void mapPartition(Iterable<String> iterable, Collector<String> collector) {
                for(String s : iterable){
                    s = s.toUpperCase() + " by zhangyunhao";
                    collector.collect(s);
                }
            }
        });
        text2.print();

        //4.以partition为粒度，进行map操作，转化为大写并,并计算line的长度。
        //4.1定义class
        class Wc{
            private String line;
            private int lineLength;
            public Wc(String line, int lineLength) {
                this.line = line;
                this.lineLength = lineLength;
            }

            @Override
            public String toString() {
                return "Wc{" + "line='" + line + '\'' + ", lineLength='" + lineLength + '\'' + '}';
            }
        }
        DataSet<Wc> text3 = text.mapPartition(new MapPartitionFunction<String, Wc>() {
            @Override
            public void mapPartition(Iterable<String> iterable, Collector<Wc> collector) {
                //注意这里转化的需要强转一下类型
                Iterator<String> iterator = iterable.iterator();
                while(iterator.hasNext()){
                    String iter = iterator.next();
                    collector.collect(new Wc(iter.toUpperCase(),iter.length()));
                }
            }
        });
        text3.print();

    }
}

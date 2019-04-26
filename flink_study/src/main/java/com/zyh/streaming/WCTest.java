package com.zyh.streaming;

import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamUtils;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.util.Iterator;

/**
 * 测试
 */
public class WCTest {

    public static void main(String[] args) throws Exception{
        final StreamExecutionEnvironment env =
                StreamExecutionEnvironment.getExecutionEnvironment();
        DataStream<Tuple2<String, Integer>> wordCounts = env.fromElements(
                new Tuple2<String, Integer>("world", 2),
                new Tuple2<String, Integer>("world", 3),
                new Tuple2<String, Integer>("hello", 4),
                new Tuple2<String, Integer>("world", 666),
                new Tuple2<String, Integer>("hello", 55),
                new Tuple2<String, Integer>("hello", 1));

        DataStream<Integer> key = wordCounts.keyBy(0).map(new MapFunction<Tuple2<String, Integer>, Integer>() {
            @Override
            public Integer map(Tuple2<String, Integer> value) throws Exception {
                return value.f1;
            }
        });

        DataStream<Integer> key2 = key.filter(new FilterFunction<Integer>() {
            @Override
            public boolean filter(Integer integer) throws Exception {
                return false;
            }
        });

        //用于调试流数据处理
        Iterator<Integer> myOutput = DataStreamUtils.collect(key);
        while(myOutput.hasNext()){
            System.out.println(myOutput.next());
        }
    }
}

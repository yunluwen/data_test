package com.zyh.batch;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
//使用分离flink自带的Tuple2
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.util.Collector;

public class WC {

    public static void main(String[] args) throws Exception{
        //1:设置运行环境
        final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
        //2：创建DataSet<String>
        DataSet<String> text = env.fromElements(
                "Who's there?",
                "I think I hear them. Stand, ho! Who's there?");

        DataSet<Tuple2<String, Integer>> wordCounts = text
                .flatMap(new LineSplitter())   //自定义函数操作
                .groupBy(0)             //key的选择
                .sum(1);

        wordCounts.print();
    }

    public static class LineSplitter implements FlatMapFunction<String, Tuple2<String, Integer>> {
        @Override
        public void flatMap(String line, Collector<Tuple2<String, Integer>> out) {
            for (String word : line.split(" ")) {
                out.collect(new Tuple2<String, Integer>(word, 1));
            }
        }
    }
}

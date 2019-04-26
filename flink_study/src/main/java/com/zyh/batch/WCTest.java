package com.zyh.batch;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.AggregateOperator;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.api.java.operators.FlatMapOperator;
import org.apache.flink.api.java.operators.MapOperator;
import org.apache.flink.util.Collector;
import scala.Tuple2;

/**
 * 测试
 */
public class WCTest {

    public static void main(String[] args) {
        //1：设置运行环境
        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
        //2：创造测试数据
        DataSource<String> text = env.fromElements(
                "To be, or not to be,--that is the question:--",
                "Whether 'tis nobler in the mind to suffer",
                "The slings and arrows of outrageous fortune",
                "Or to take arms against a sea of troubles,");
        //3：进行wc计算
        FlatMapOperator<String,String> words =
                text.flatMap(new FlatMapFunction<String, String>() {
            @Override
            public void flatMap(String s, Collector<String> collector) {
                for(String s1 : s.toLowerCase().split("\\W+")){
                    collector.collect(s1);
                }
            }
        });

        MapOperator<String,Object> map =
                words.map(new MapFunction<String, Object>() {
            @Override
            public Object map(String s) {
                return new Tuple2<String,Integer>(s,1);
            }
        });



//        AggregateOperator<Object> result =
//                map.groupBy(0).sum(1);

        try {
            map.print();
        }catch (Exception e){

        }

    }
}

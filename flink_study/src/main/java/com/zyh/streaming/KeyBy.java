package com.zyh.streaming;

import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * DataStream
 * Flink程序看起来像是转换数据集合的常规程序。每个程序包含相同的基本部分：
 *
 * 获得一个execution environment，
 * 加载/创建初始数据，
 * 指定此数据的转换，
 * 指定放置计算结果的位置，
 * 触发程序执行
 *
 * 注意：Flink的数据模型不基于键值对。因此，您无需将数据集类型物理打包到键和值中。
 * 键是“虚拟的”：它们被定义为实际数据上的函数，以指导分组操作符。
 */
public class KeyBy {

    public static void main(String[] args) {
        final StreamExecutionEnvironment env =
                StreamExecutionEnvironment.getExecutionEnvironment();

        DataStream<Tuple3<Integer,String,Long>> stream = env.fromElements(
                new Tuple3<Integer,String,Long>(1,"a",4L),
                new Tuple3<Integer,String,Long>(1,"b",2L),
                new Tuple3<Integer,String,Long>(3,"c",3L),
                new Tuple3<Integer,String,Long>(2,"d",6L),
                new Tuple3<Integer,String,Long>(2,"e",8L)
        );
        /**
         * 定义元组的键
         */
        stream.keyBy(0);     //元组在第一个字段（整数类型）上分组。
        stream.keyBy(0,1);   //将元组分组在由第一个和第二个字段组成的复合键上。

        //对于带有嵌套元组的DataStream
        DataStream<Tuple3<Tuple2<Integer, Float>,String,Long>> ds;
        //如果使用keyBy(0)将导致系统使用full Tuple2作为键（以Integer和Float为键）。
        // 如果要“导航”到嵌套中Tuple2，则必须使用下面解释的字段表达式键。

        DataStream<WordCount> ds2 = env.fromElements();
        /**
         * 字段表达式语法：
         *
         * 按字段名称选择POJO字段。例如，"user"指POJO类型的“用户”字段。
         *
         * 按字段名称或0偏移字段索引选择元组字段。例如"f0"，分别"5"引用Java Tuple类型的第一个和第六个字段。
         *
         * 您可以在POJO和Tuples中选择嵌套字段。例如，"user.zip"指POJO的“zip”字段，其存储在POJO类型的“user”字段中。
         * 支持POJO和元组的任意嵌套和混合，例如"f1.user.zip"或"user.f3.1.zip"。
         *
         * 您可以使用"*"通配符表达式选择完整类型。这也适用于非Tuple或POJO类型的类型。
         */
        ds2.keyBy("count"); //wordCount类中的count字段
        ds2.keyBy("complex");  //递归选择POJO类型的字段复合体的所有字段ComplexNestedClass。
        ds2.keyBy("complex.word.f2");//选择嵌套的最后一个字段Tuple3。
        ds2.keyBy("complex.someFloat");//选择ComplexNestedClass的someFloat字段

        /**
         * 使用键选择器功能定义键
         * 第一个参数是数据的类型，第二个参数是键的类型
         */
        KeyedStream<WordCount,String> keyed = ds2
                .keyBy(new KeySelector<WordCount, String>() {
                    public String getKey(WordCount wc) {
                        return wc.complex.word.f2;
                    }
                });
    }

    /**
     * 将数据封装在pojo中
     */
    public static class ComplexNestedClass {
        public Integer someNumber;
        public float someFloat;
        public Tuple3<Long, Long, String> word;
    }

    public static class WordCount {
        public ComplexNestedClass complex; //nested POJO
        public int count;
    }

}

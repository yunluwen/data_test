package com.zyh.batch;

import org.apache.flink.api.common.functions.*;
import org.apache.flink.api.common.operators.Order;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.aggregation.Aggregations;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.util.Collector;

/**
 * 转换操作
 */
public class TransformationOperation {

    public static void main(String[] args) throws Exception{
        //1：设置运行环境
        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
        //testMap(env);
        //testMapPartition(env);
        //testFilter(env);
        //testReduce(env);
        //testDistinct(env);
        //testAggregate(env);
        //testJoin(env);         //有问题


        //testunion(env);
        //testpartitionByHash(env);
        //testpartitionByRange(env);
        //testsortPartition(env);

        //testfirst(env);          //有问题
    }

    /**
     * 将一行元素拆分成多行元素
     */
    public static class LineSplitter implements FlatMapFunction<String, Tuple2<String, Integer>> {
        @Override
        public void flatMap(String line, Collector<Tuple2<String, Integer>> out) {
            for (String word : line.split(" ")) {
                out.collect(new Tuple2<String, Integer>(word, 1));
            }
        }
    }

    /**
     * map算子:采用一个元素并生成一个元素
     * flatmap算子：采用一个元素并生成零个，一个或多个元素。
     * @param env
     */
    public static void testMap(ExecutionEnvironment env) throws Exception{
        DataSet<String> text = env.fromElements("1 2 3 4 5 6 7 8 9");

        /**
         * 这里注意flatMap的返回类型
         */
        DataSet<String> flatmap = text.flatMap(new FlatMapFunction<String, String>() {
            @Override
            public void flatMap(String value, Collector<String> out) {
                for (String s : value.split(" ")) {
                    out.collect(s);
                }
            }
        });

        /**
         * 这里注意map的返回类型
         */
        DataSet<Integer> map = flatmap.map(new MapFunction<String, Integer>() {
            @Override
            public Integer map(String value) {
                return Integer.parseInt(value);
            }
        });
        map.print();
    }

    /**
     * mapPartition算子：
     * 在单个函数调用中转换并行分区。该函数将分区作为Iterable流来获取，
     * 并且可以生成任意数量的结果值。每个分区中的元素数量取决于并行度和先前的操作。
     * @param env
     * @throws Exception
     */
    public static void testMapPartition(ExecutionEnvironment env) throws Exception{
        DataSet<String> data = env.fromElements("1 2 3 4 5 6 7 8 9");
        /**
         * 先对数据进行拆分操作
         */
        DataSet<String> flatmap = data.flatMap(new FlatMapFunction<String, String>() {
            @Override
            public void flatMap(String value, Collector<String> out) {
                for (String s : value.split(" ")) {
                    out.collect(s);
                }
            }
        });

        /**
         * 注意mapPartition的结果返回类型
         */
        DataSet<Long> result = flatmap.mapPartition(new MapPartitionFunction<String, Long>() {
            @Override
            public void mapPartition(Iterable<String> iterable,
                                     Collector<Long> collector) {
                long c = 0;
                for(String s:iterable){
                    c++;
                }
                collector.collect(c);
            }
        });
        result.print();
    }

    /**
     * filter算子：计算每个元素的布尔函数，并保留函数返回true的元素。
     * 重要信息：系统假定该函数不会修改应用谓词的元素。违反此假设可能会导致错误的结果。
     * @param env
     * @throws Exception
     */
    public static void testFilter(ExecutionEnvironment env) throws Exception{
        DataSet<String> data = env.fromElements("1 2 3 4 5 6 7 8 9");
        /**
         * 先将数据进行拆分
         * 并进行数据类型转换操作
         */
        DataSet<Integer> flatmap = data.flatMap(new FlatMapFunction<String, Integer>() {
            @Override
            public void flatMap(String value, Collector<Integer> out) {
                for (String s : value.split(" ")) {
                    out.collect(Integer.parseInt(s));
                }
            }
        });

        DataSet<Integer> filter = flatmap.filter(new FilterFunction<Integer>() {
            @Override
            public boolean filter(Integer s) throws Exception {
                //过滤2的倍数
                return s%2 == 0;
            }
        });
        filter.print();
    }

    /**
     * reduce算子：
     * 通过将两个元素重复组合成一个元素，将一组元素组合成一个元素。
     * Reduce可以应用于完整数据集或分组数据集。
     * @param env
     * @throws Exception
     */
    public static void testReduce(ExecutionEnvironment env) throws Exception{
        DataSet<String> data = env.fromElements("1 2 3 4 5 6 7 8 9 10");
        /**
         * 先将数据进行拆分
         * 并进行数据类型转换操作
         */
        DataSet<Integer> flatmap = data.flatMap(new FlatMapFunction<String, Integer>() {
            @Override
            public void flatMap(String value, Collector<Integer> out) {
                for (String s : value.split(" ")) {
                    out.collect(Integer.parseInt(s));
                }
            }
        });

        DataSet<Integer> reduce = flatmap.reduce(new ReduceFunction<Integer>() {
            @Override
            public Integer reduce(Integer t1, Integer t2) throws Exception {
                return t1+t2;
            }
        });

        reduce.print();
    }

    /**
     * ReduceGroup算子：
     * 将一组元素组合成一个或多个元素。ReduceGroup可以应用于完整数据集或分组数据集。
     * @param env
     * @throws Exception
     */
    public static void testReduceGroup(ExecutionEnvironment env) throws Exception{

    }

    /**
     * aggregate算子：聚合操作
     * 将一组值聚合为单个值。聚合函数可以被认为是内置的reduce函数。
     * 聚合可以应用于完整数据集或分组数据集。
     * @param env
     * @throws Exception
     */
    public static void testAggregate(ExecutionEnvironment env) throws Exception{
        DataSet<String> data = env.fromElements("a b a d c a d b c a");
        /**
         * 注意aggregate算子的返回类型
         */
        DataSet<Tuple2<String,Integer>> result = data.flatMap(new LineSplitter()).groupBy(0)
                .aggregate(Aggregations.SUM,1);  //聚合操作
        result.print();
    }

    /**
     * distinct算子：去重
     * 返回数据集的不同元素。它相对于元素的所有字段或字段子集从输入DataSet中删除重复条目。
     * @param env
     * @throws Exception
     */
    public static void testDistinct(ExecutionEnvironment env) throws Exception{
        DataSet<String> data = env.fromElements("7 2 8 4 5 9 7 8 9 10");
        DataSet<Integer> flatmap = data.flatMap(new FlatMapFunction<String, Integer>() {
            @Override
            public void flatMap(String value, Collector<Integer> out) {
                for (String s : value.split(" ")) {
                    out.collect(Integer.parseInt(s));
                }
            }
        });
        DataSet<Integer> distinct = flatmap.distinct();
        distinct.print();
    }

    /**
     * join算子：
     * 通过创建在其 (键上) 相等的所有元素对来连接两个数据集。
     * ***可选***地使用JoinFunction将元素对转换为单个元素，
     * 或使用FlatJoinFunction将元素对转换为任意多个（包括无）元素。请参阅键部分以了解如何定义连接键。
     * @param env
     * @throws Exception
     */
    public static void testJoin(ExecutionEnvironment env) throws Exception{
        DataSet<Tuple2<String,Integer>> data1 = env.fromElements(
                new Tuple2<String,Integer>("a",1),
                new Tuple2<String,Integer>("b",2),
                new Tuple2<String,Integer>("c",3),
                new Tuple2<String,Integer>("d",4),
                new Tuple2<String,Integer>("c",5));

        DataSet<Tuple2<String,String>> data2 = env.fromElements(
                new Tuple2<String,String>("a","hhh"),
                new Tuple2<String,String>("b","hehe"),
                new Tuple2<String,String>("c","haha"),
                new Tuple2<String,String>("d","lele"),
                new Tuple2<String,String>("c","didi"));

        data1.join(data2).where(0).equalTo("a").print();
    }

    public static void testleftOuterJoin(ExecutionEnvironment env) throws Exception{

    }

    public static void testcoGroup(ExecutionEnvironment env) throws Exception{

    }

    /**
     * union算子：
     * 生成两个数据集的并集。
     * @param env
     * @throws Exception
     */
    public static void testunion(ExecutionEnvironment env) throws Exception{
        DataSet<String> data1 = env.fromElements("7 2 8 4 5 9 7 8 9 10");
        DataSet<String> data2 = env.fromElements("17 12 18 4 5 9 7 8 9 10");
        DataSet<String> result = data1.union(data2);
        result.print();
    }

    /**
     * partitionByHash算子:
     * 散列分区给定键上的数据集。键可以指定为位置键，表达键和键选择器功能。
     * @param env
     * @throws Exception
     */
    public static void testpartitionByHash(ExecutionEnvironment env) throws Exception{
        DataSet<Tuple2<String,Integer>> data = env.fromElements(
                new Tuple2<String,Integer>("a",1),
                new Tuple2<String,Integer>("b",2),
                new Tuple2<String,Integer>("c",3),
                new Tuple2<String,Integer>("d",4),
                new Tuple2<String,Integer>("c",5));
        DataSet<Integer> result = data.partitionByHash(0)
                .mapPartition(new MapPartitionFunction<Tuple2<String, Integer>, Integer>() {
                    @Override
                    public void mapPartition(
                            Iterable<Tuple2<String, Integer>> iterable,
                            Collector<Integer> collector) throws Exception {
                        for(Tuple2<String, Integer> tuple2:iterable){
                            collector.collect(tuple2.f1);
                        }
                    }
                });
        result.print();
    }

    /**
     * partitionByRange算子:
     * 范围分区给定键上的数据集。键可以指定为位置键，表达键和键选择器功能。
     * @param env
     * @throws Exception
     */
    public static void testpartitionByRange(ExecutionEnvironment env) throws Exception{
        DataSet<Tuple2<String,Integer>> data = env.fromElements(
                new Tuple2<String,Integer>("a",1),
                new Tuple2<String,Integer>("a",2),
                new Tuple2<String,Integer>("c",3),
                new Tuple2<String,Integer>("b",2),
                new Tuple2<String,Integer>("c",5));
        DataSet<Integer> result = data.partitionByRange(0)
                .mapPartition(new MapPartitionFunction<Tuple2<String, Integer>, Integer>() {
                    @Override
                    public void mapPartition(
                            Iterable<Tuple2<String, Integer>> iterable,
                            Collector<Integer> collector) throws Exception {
                        for(Tuple2<String, Integer> tuple2:iterable){
                            collector.collect(tuple2.f1);
                        }
                    }
                });
        result.print();
    }

    /**
     * partitionCustom算子:
     * 手动指定数据分区。
     * 注意：此方法仅适用于单个字段键。
     * @param env
     * @throws Exception
     */
    public static void testpartitionCustom(ExecutionEnvironment env) throws Exception{

    }

    /**
     * sortPartition算子:
     * 本地按指定顺序对指定字段上的数据集的所有分区进行排序。
     * 可以将字段指定为元组位置或字段表达式。通过链接sortPartition() 调用来完成对多个字段的排序。
     * @param env
     * @throws Exception
     */
    public static void testsortPartition(ExecutionEnvironment env) throws Exception{
        DataSet<Tuple2<String,Integer>> data = env.fromElements(
                new Tuple2<String,Integer>("a",5),
                new Tuple2<String,Integer>("a",2),
                new Tuple2<String,Integer>("c",3),
                new Tuple2<String,Integer>("b",2),
                new Tuple2<String,Integer>("c",5));
        //根据第二个字段进行排序分组操作
        DataSet<Integer> result = data.sortPartition(1, Order.ASCENDING)
                .mapPartition(new MapPartitionFunction<Tuple2<String, Integer>, Integer>() {
                    @Override
                    public void mapPartition(Iterable<Tuple2<String, Integer>> iterable, Collector<Integer> collector) throws Exception {
                        for(Tuple2<String, Integer> tuple2:iterable){
                            collector.collect(tuple2.f1);
                        }
                    }
                });
        result.print();
    }

    /**
     * first 算子:
     * 返回数据集的前n个（任意）元素。First-n可以应用于常规数据集，
     * 分组数据集或分组排序数据集。分组键可以指定为键选择器功能或字段位置键。
     * @param env
     * @throws Exception
     */
    public static void testfirst(ExecutionEnvironment env) throws Exception{
        DataSet<String> data = env.fromElements("a b c d e f g h i j");

        DataSet<String> flatmap = data.flatMap(new FlatMapFunction<String, String>() {
            @Override
            public void flatMap(String s, Collector<String> collector) throws Exception {
                for(String string:s.split(" ")){
                    collector.collect(s);
                }
            }
        });

        DataSet<String> firstN = flatmap.first(3);
        firstN.print();

    }


}

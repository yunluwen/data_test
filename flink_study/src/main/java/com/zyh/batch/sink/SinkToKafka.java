package com.zyh.batch.sink;

import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;

/**
 * 将Dataset中的数据sink到kafka中
 */
public class SinkToKafka {

    public static void main(String[] args) throws Exception{
        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
        DataSet<String> data =
                env.fromElements(
                        "aaa","bbb","ccc");

        //DataSet提供了output方法，将数据输出到自定的sink
        data.output(
                KafkaOutputFormat.buildKafkaOutputFormat()
                        .setBootstrapServers("localhost:9092")
                        .setTopic("Test_Topic_1")
                        .setAcks("all")
                        .setBatchSize("10")
                        .setBufferMemory("10240")
                        .setLingerMs("1")
                        .setRetries("0")
                        .finish()
        );
        env.execute();
    }
}

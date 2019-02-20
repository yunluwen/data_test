package kafka;

import org.apache.kafka.clients.producer.*;

import java.util.Properties;

/**
 * Kafka
 * 高级API
 * 新API:生产者
 *
 * Kafka源码中的Producer Record定义：
 *  1、ProducerRecord 含义: 发送给Kafka Broker的key/value 值对
 *
 *  2.内部数据结构：
 *      -- Topic （名字）
 *      -- PartitionID ( 可选)
 *      -- Key[( 可选 )
 *      -- Value
 *
 *  3.生产者记录（简称PR）的发送逻辑:
 *      <1> 若指定Partition ID,则PR被发送至指定Partition
 *      <2> 若未指定Partition ID,但指定了Key, PR会按照hasy(key)发送至对应Partition
 *      <3> 若既未指定Partition ID 也没指定Key，PR会按照round-robin模式发送到每个Partition
 *      <4> 若同时指定了Partition ID和Key, PR只会发送到指定的Partition (Key不起作用，代码逻辑决定)
 *
 *  4.生产者记录(PR)的实现：
 *      针对3,提供三种构造函数形参:
 *      -- ProducerRecord(topic, partition, key, value)
 *      -- ProducerRecord(topic, key, value)
 *      -- ProducerRecord(topic, value)
 */
public class CustomerProducer {

    public static void main(String[] args) {

//        ProducerConfig   //生产者配置信息类

        Properties props = new Properties();
        // Kafka服务端的主机名和端口号
        props.put("bootstrap.servers", "localhost:9092");
        // 等待所有副本节点的应答
        props.put("acks", "all");
        // 消息发送最大尝试次数
        props.put("retries", 0);
        // 一批消息处理大小
        props.put("batch.size", 16384);
        // 请求延时
        props.put("linger.ms", 1);
        // 发送缓存区内存大小
        props.put("buffer.memory", 33554432);

        // key序列化
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        // value序列化
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        // 1、普通生产者
        Producer<String, String> producer = new KafkaProducer<>(props);
        for (int i = 0; i < 50; i++) {
            //发送数据
            producer.send(new ProducerRecord<String, String>("testaaa",   //
                    Integer.toString(i), "hello world-" + i));
        }

        // 2、带回调函数的生产者
        for (int i = 0; i < 50; i++) {
            //发送数据
            //回调函数
            producer.send(new ProducerRecord<String, String>("test11",
                    "hello "+Integer.toString(i)), (recordMetadata, e) -> {   //lambada表达式，回调函数
                        if (recordMetadata != null) {
                            System.err.println(recordMetadata.partition() + "---" + recordMetadata.offset());
                        }
                    });
        }

        // 3、自定义分区生产者（将数据写到指定的分区）
        // 自定义分区
        props.put("partitioner.class", "org.apache.kafka.clients.producer.CustomPartitioner");

        Producer<String, String> producer2 = new KafkaProducer<>(props);
        producer2.send(new ProducerRecord<String, String>("test22", "1", "atguigu"));

        producer.close();


    }
}

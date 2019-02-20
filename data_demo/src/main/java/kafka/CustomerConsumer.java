package kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;

import java.util.Collections;
import java.util.Properties;

/**
 * 高级API:
 * 新API:消费者
 */
public class CustomerConsumer {

    public static void main(String[] args) {

        Properties props = new Properties();
        // 定义kakfa 服务的地址，不需要将所有broker指定上
        props.put("bootstrap.servers", "localhost:9092");
        // 制定consumer group
        props.put("group.id", "test");    ///同一个组的消费一个topic,一个组员消费一个分区的数据

        // 重复消费的话需要加这一行配置
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,"earliest");  //重置offset,从最早的offset开始消费

        // 是否自动提交offset
        props.put("enable.auto.commit", "true");

        // 自动提交offset的时间间隔，注意这个参数的设置，可能会造成数据重复读取的问题
        props.put("auto.commit.interval.ms", "1000");
        // key的序列化类（反序列化）
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        // value的序列化类（反序列化）
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        // 定义consumer消费者
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);

        // 订阅方式1、消费者订阅的topic, 可同时订阅多个
        // consumer.subscribe(Arrays.asList("first", "second","third"));

        //订阅方式2、
        consumer.assign(Collections.singleton(new TopicPartition("test11",0)));
        //指定某一个topic的分区，从第几个offset开始消费
        consumer.seek(new TopicPartition("test11",0),2);

        while (true) {
            // 读取数据，读取超时时间为100ms
            ConsumerRecords<String, String> records = consumer.poll(100);

            for (ConsumerRecord<String, String> record : records) {
                System.out.printf("offset = %d, key = %s, value = %s%n",
                        record.offset(), record.key(), record.value());
            }
        }
    }
}

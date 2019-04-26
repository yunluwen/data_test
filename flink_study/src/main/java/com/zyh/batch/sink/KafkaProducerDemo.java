package com.zyh.batch.sink;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.List;
import java.util.Properties;

/**
 * 将dataset的数据传递到kafka
 * 不要采用将dataset的数据collect到本地的一个list,在sink到其他地方
 * 这种方式只适合小数据量的场景，对于大规模数据的场景，根本不可行
 */
public class KafkaProducerDemo {

    private static Config config = ConfigFactory.load();

    /**
     * kafka生产者
     * @param resLS
     */
    public static void kafkaProducer(List<String> resLS) {
        Properties props = new Properties();
        props.setProperty("bootstrap.servers", "localhost:9092");
        props.setProperty("acks", "all");
        props.setProperty("retries", "0");
        props.setProperty("batch.size", "10");
        props.setProperty("linger.ms", "1");
        props.setProperty("buffer.memory", "10240");
        props.setProperty("key.serializer",
                "org.apache.kafka.common.serialization.StringSerializer");
        props.setProperty("value.serializer",
                "org.apache.kafka.common.serialization.StringSerializer");

        Producer<String, String> producer = new org.apache.kafka.clients.producer.KafkaProducer<String, String>(props);

        for (String context : resLS) {
            producer.send(new ProducerRecord<String,String>(
                    "Test_Topic", String.valueOf(System.currentTimeMillis()), context));
        }

        producer.close();

    }
}

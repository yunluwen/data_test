package kafka.interceptor;

import java.util.Map;
import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

/**
 * kafka producer拦截器
 * 实时数据分析可用
 *
 * interceptor可能被运行在多个线程中，因此在具体实现时用户需要自行确保线程安全。
 * 另外倘若指定了多个interceptor，则producer将按照指定顺序调用它们，
 * 并仅仅是捕获每个interceptor可能抛出的异常记录到错误日志中而非在向上传递。这在使用过程中要特别留意。
 *
 * 在每条消息之前添加时间戳
 */
public class TimeInterceptor implements ProducerInterceptor<String, String> {

    /**
     * 获取配置信息和初始化数据时调用。
     * @param configs
     */
    @Override
    public void configure(Map<String, ?> configs) {

    }

    /**
     * 该方法封装进KafkaProducer.send方法中，即它运行在用户主线程中。
     * Producer确保在消息被序列化以及计算分区前调用该方法。
     * 用户可以在该方法中对消息做任何操作，但最好保证不要修改消息所属的topic和分区，否则会影响目标分区的计算
     * @param record
     * @return
     */
    @Override
    public ProducerRecord<String, String> onSend(ProducerRecord<String, String> record) {

        // 创建一个新的record，把时间戳写入消息体的最前部
        return new ProducerRecord(record.topic(), record.partition(), record.timestamp(), record.key(),
                System.currentTimeMillis() + "," + record.value().toString());
    }

    /**
     * 该方法会在消息被应答或消息发送失败时调用，并且通常都是在producer回调逻辑触发之前。
     * onAcknowledgement运行在producer的IO线程中，因此不要在该方法中放入很重的逻辑，否则会拖慢producer的消息发送效率
     * @param metadata
     * @param exception
     */
    @Override
    public void onAcknowledgement(RecordMetadata metadata, Exception exception) {

    }

    /**
     * 关闭interceptor，主要用于执行一些资源清理工作
     */
    @Override
    public void close() {

    }
}

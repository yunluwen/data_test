package kafka;

import kafka.api.FetchRequestBuilder;
import kafka.cluster.BrokerEndPoint;
import kafka.javaapi.*;
import kafka.javaapi.consumer.SimpleConsumer;
import kafka.javaapi.message.ByteBufferMessageSet;
import kafka.message.MessageAndOffset;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 低级API
 * Kafka消费者
 * 根据指定的Topic,Partition,Offset来获取数据
 */
public class LowerConsumer {

    public static void main(String[] args) {

        //定义相关参数
        List<String> brokers = new ArrayList<>();  //kafka集群
        brokers.add("lcoalhost");

        //端口号
        int port = 9092;

        //主题
        String topic = "test22";

        //分区
        int partition = 0;

        //offset
        long offset = 2;

        BrokerEndPoint leader = findLeader(brokers,port,topic,partition);
        getData(leader,topic,partition,offset);

    }

    //找分区Leader
    public static BrokerEndPoint findLeader(List<String> brokers, int port, String topic, int partition){
        for(String broker : brokers){

            //创建获取分区leader的消费者对象，核心，连接具体的某一个broker
            SimpleConsumer getLeader = new SimpleConsumer(broker,port,
                    1000,1024*4,"getLeader");
            //创建一个主题元数据信息请求
            TopicMetadataRequest topicMetadataRequest = new TopicMetadataRequest(Collections.singletonList(topic));
            //获取主题元数据返回值
            TopicMetadataResponse topicMetadataResponse = getLeader.send(topicMetadataRequest);
            //解析元数据返回值
            List<TopicMetadata> topicMetadatas = topicMetadataResponse.topicsMetadata();
            //遍历主题元数据
            for(TopicMetadata topicMetadata : topicMetadatas){
                //获取分区元数据
                List<PartitionMetadata> partitionMetadatas = topicMetadata.partitionsMetadata();
                for(PartitionMetadata partitionMetadata : partitionMetadatas){
                    //获取指定分区leader
                    if(partitionMetadata.partitionId() == partition){
                        return partitionMetadata.leader();
                    }
                }
            }
        }
        return null;
    }

    /**
     * 获取数据
     */
    public static void getData(BrokerEndPoint leader,String topic,int partition,long offset){

        if(leader == null){
            return;
        }

        String host = leader.host();
        int port = leader.port();

        //获取数据的消费者对象
        SimpleConsumer getData = new SimpleConsumer(host,port,1000,1024*4,"getData");
        //创建获取数据的对象,可以多个addFetch,来自于不同的topic
        kafka.api.FetchRequest fetchRequest = new FetchRequestBuilder()
                .addFetch(topic,partition,offset,100).build();
        //获取数据返回值
        FetchResponse fetchResponse = getData.fetch(fetchRequest);
        //解析数据返回值,具体的某一个topic的Fetch
        ByteBufferMessageSet byteBufferMessageSet = fetchResponse.messageSet(topic,partition);
        //遍历并打印
        for(MessageAndOffset messageAndOffset : byteBufferMessageSet){
            long offsetData = messageAndOffset.offset();
            //解析数据为字符串
            ByteBuffer byteBuffer = messageAndOffset.message().payload();
            byte[] bytes = new byte[byteBuffer.limit()];
            byteBuffer.get(bytes);

            System.out.println(offsetData+"-------------"+new String(bytes));
        }

    }

}

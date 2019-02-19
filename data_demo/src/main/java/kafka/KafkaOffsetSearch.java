package kafka;

import kafka.api.PartitionOffsetRequestInfo;
import kafka.common.TopicAndPartition;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Date;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.TreeMap;

import kafka.javaapi.OffsetResponse;
import kafka.javaapi.PartitionMetadata;
import kafka.javaapi.TopicMetadata;
import kafka.javaapi.TopicMetadataRequest;
import kafka.javaapi.consumer.SimpleConsumer;

public class KafkaOffsetSearch {

    public static void main(String[] args) {
        System.out.println(getPartitonAndOffset());
    }


    public static Map<String,String> getPartitonAndOffset() {

        int port = 9200;
        long timestamp = 1548405743126l; //要查询的时间
        String topic = "test";
        List<String> seeds = new ArrayList<>(); //kafka broke地址
        seeds.add("127.0.0.1");

        KafkaOffsetSearch kot = new KafkaOffsetSearch();

        TreeMap<Integer,PartitionMetadata> metadatas = kot.findLeader(seeds, port, topic);
        Map<String,String> map = new HashMap<String,String>();
        List<Long> offSetList = new ArrayList<>();
        for (Entry<Integer,PartitionMetadata> entry : metadatas.entrySet()) {
            int partition = entry.getKey();
            String leadBroker = entry.getValue().leader().host();
            String clientName = "Client_" + topic + "_" + partition;
            SimpleConsumer consumer = new SimpleConsumer(leadBroker, port, 100000,64 * 1024, clientName);
            long readOffset = getLastOffset(consumer, topic, partition,
                    timestamp, clientName);
            offSetList.add(readOffset);
            map.put(partition+"", readOffset+"");
            System.out.println(partition+":"+readOffset);
            if(consumer!=null)consumer.close();
        }

        return map;

    }



    public static long getLastOffset(SimpleConsumer consumer, String topic,
                                     int partition, long whichTime, String clientName) {
        TopicAndPartition topicAndPartition = new TopicAndPartition(topic,
                partition);
        Map<TopicAndPartition, PartitionOffsetRequestInfo> requestInfo = new HashMap<TopicAndPartition, PartitionOffsetRequestInfo>();
        requestInfo.put(topicAndPartition, new PartitionOffsetRequestInfo(
                whichTime, 1));
        kafka.javaapi.OffsetRequest request = new kafka.javaapi.OffsetRequest(
                requestInfo, kafka.api.OffsetRequest.CurrentVersion(),
                clientName);
        OffsetResponse response = consumer.getOffsetsBefore(request);

        if (response.hasError()) {
            System.out
                    .println("Error fetching data Offset Data the Broker. Reason: "
                            + response.errorCode(topic, partition));
            return 0;
        }
        long[] offsets = response.offsets(topic, partition);
        return offsets[0];
    }


    private TreeMap<Integer,PartitionMetadata> findLeader(List<String> a_seedBrokers,
                                                          int a_port, String a_topic) {
        TreeMap<Integer, PartitionMetadata> map = new TreeMap<Integer, PartitionMetadata>();
        loop: for (String seed : a_seedBrokers) {
            SimpleConsumer consumer = null;
            try {
                consumer = new SimpleConsumer(seed, a_port, 100000, 64 * 1024,
                        "leaderLookup"+new Date().getTime());
                List<String> topics = Collections.singletonList(a_topic);
                TopicMetadataRequest req = new TopicMetadataRequest(topics);
                kafka.javaapi.TopicMetadataResponse resp = consumer.send(req);

                List<TopicMetadata> metaData = resp.topicsMetadata();
                for (TopicMetadata item : metaData) {
                    for (PartitionMetadata part : item.partitionsMetadata()) {
                        map.put(part.partitionId(), part);
                    }
                }
            } catch (Exception e) {
                System.out.println("Error communicating with Broker [" + seed
                        + "] to find Leader for [" + a_topic + ", ] Reason: " + e);
            } finally {
                if (consumer != null)
                    consumer.close();
            }
        }
        return map;
    }

}
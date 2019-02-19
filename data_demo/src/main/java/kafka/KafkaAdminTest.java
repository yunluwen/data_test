package kafka;

import org.apache.kafka.clients.admin.*;
import org.apache.kafka.common.KafkaFuture;
import org.apache.kafka.common.Node;
import org.apache.kafka.common.config.ConfigResource;

import java.util.*;
import java.util.concurrent.ExecutionException;

/**
 * Kafka 0.11 客户端集群管理工具AdminClient
 */
public class KafkaAdminTest {


    private static final String TEST_TOPIC = "testaaa";



    public static void main(String[] args) {
        Properties props = new Properties();
        props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");


        try {
            AdminClient client = AdminClient.create(props);
            //整个集群详情
//            describeCluster(client);
//            System.out.println("------------------");
//            //创建topic
//            createTopics(client);
//            System.out.println("------------------");
            //列出所有的topic
            listAllTopics(client);
            System.out.println("------------------");
            //某个topic详情
//            describeTopics(client);
//            System.out.println("------------------");
//            //给topic添加配置
//            alterConfigs(client);
//            System.out.println("------------------");
//            //配置详情
//            describeConfig(client);
//            System.out.println("------------------");
//            //删除topic
//            deleteTopics(client);
//            System.out.println("------------------");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * describe the cluster
     * @param client
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public static void describeCluster(AdminClient client) throws ExecutionException, InterruptedException {
        DescribeClusterResult ret = client.describeCluster();
        System.out.println(String.format("Cluster id: %s, controller: %s", ret.clusterId().get(), ret.controller().get()));
        System.out.println("Current cluster nodes info: ");
        for (Node node : ret.nodes().get()) {
            System.out.println(node);
        }
    }

    /**
     * describe topic‘s config
     * @param client
     */
    public static void describeConfig(AdminClient client) throws ExecutionException, InterruptedException {
        DescribeConfigsResult ret = client.describeConfigs(Collections.singleton(new ConfigResource(ConfigResource.Type.TOPIC, TEST_TOPIC)));
        Map<ConfigResource, Config> configs = ret.all().get();
        for (Map.Entry<ConfigResource, Config> entry : configs.entrySet()) {
            ConfigResource key = entry.getKey();
            Config value = entry.getValue();
            System.out.println(String.format("Resource type: %s, resource name: %s", key.type(), key.name()));
            Collection<ConfigEntry> configEntries = value.entries();
            for (ConfigEntry each : configEntries) {
                System.out.println(each.name() + " = " + each.value());
            }
        }

    }

    /**
     * alter config for topics
     * @param client
     */
    public static void alterConfigs(AdminClient client) throws ExecutionException, InterruptedException {
        Config topicConfig = new Config(Arrays.asList(new ConfigEntry("cleanup.policy", "compact")));
        client.alterConfigs(Collections.singletonMap(
                new ConfigResource(ConfigResource.Type.TOPIC, TEST_TOPIC), topicConfig)).all().get();
    }

    /**
     * delete the given topics
     * @param client
     */
    public static void deleteTopics(AdminClient client) throws ExecutionException, InterruptedException {
        KafkaFuture<Void> futures = client.deleteTopics(Arrays.asList(TEST_TOPIC)).all();
        futures.get();
    }

    /**
     * describe the given topics
     * @param client
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public static void describeTopics(AdminClient client) throws ExecutionException, InterruptedException {
        DescribeTopicsResult ret = client.describeTopics(Arrays.asList(TEST_TOPIC, "__consumer_offsets"));
        Map<String, TopicDescription> topics = ret.all().get();
        for (Map.Entry<String, TopicDescription> entry : topics.entrySet()) {
            System.out.println(entry.getKey() + " ===> " + entry.getValue());
        }
    }

    /**
     * create multiple sample topics
     * @param client
     */
    public static void createTopics(AdminClient client) throws ExecutionException, InterruptedException {
        //注意创建的topic的分区数要小于等于kafka的集群个数，否则会出现异常
        NewTopic newTopic = new NewTopic(TEST_TOPIC, 1, (short)1);
        CreateTopicsResult ret = client.createTopics(Arrays.asList(newTopic));
        ret.all().get();
    }

    /**
     * print all topics in the cluster
     * @param client
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public static void listAllTopics(AdminClient client) throws ExecutionException, InterruptedException {
        ListTopicsOptions options = new ListTopicsOptions();
        options.listInternal(true); // includes internal topics such as __consumer_offsets
        ListTopicsResult topics = client.listTopics(options);
        Set<String> topicNames = topics.names().get();
        System.out.println("Current topics in this cluster: " + topicNames);
    }

}

package com.spark.study.streaming.support

import org.apache.kafka.clients.consumer.{ConsumerConfig, ConsumerRecord}
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerConfig}
import org.apache.kafka.common.serialization.{IntegerDeserializer, IntegerSerializer, StringDeserializer, StringSerializer}
import org.apache.spark.streaming.StreamingContext
import org.apache.spark.streaming.dstream.DStream
import org.apache.spark.streaming.kafka010.ConsumerStrategies.Subscribe
import org.apache.spark.streaming.kafka010.KafkaUtils
import org.apache.spark.streaming.kafka010.LocationStrategies.PreferConsistent

import scala.collection.JavaConversions.mapAsJavaMap
import scala.collection.Map

/**
  * @note kafka配置文件
  *       服务代理（Broker）：已发布的消息保存在一组服务器中，它们被称为代理（Broker）或Kafka集群。
  *       话题（Topic）：是特定类型的消息流。消息是字节的有效负载（Payload），话题是消息的分类名或种子（Feed）名。
  *       生产者（Producer）：是能够发布消息到话题的任何对象。
  *       消费者（Consumer）：可以订阅一个或多个话题，并从Broker拉数据，从而消费这些已发布的消息。
  */
object KafkaSupport {//extends ConfManager{

  //broker info
  //val BROKER_LIST: String = "kafka01:9092,kafka02:9092,kafka03:9092"
  //测试kafka集群
  // val BROKER_LIST : String  = "10.25.89.206:9092,10.171.54.194:9092,10.251.192.99:9092"   // broker的地址
  //线上kafka集群
//  val BROKER_LIST : String  = getString("BROKER_LIST")   // broker的地址
  val BROKER_LIST : String  = "127.0.0.1:9092"

  //consumer配置
  private def getConsumerConifg(userGroup: String): Map[String, Object] = {
    Map(
      ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG -> BROKER_LIST,                         //设置要连接的broker
      ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG -> classOf[IntegerDeserializer],   //序列化key
      ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG -> classOf[StringDeserializer],  //序列化value
      ConsumerConfig.GROUP_ID_CONFIG -> userGroup,                      //当前的分组id
      ConsumerConfig.AUTO_OFFSET_RESET_CONFIG -> "latest",              //自动把offset设为最大的offset
      ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG -> Boolean.box(false)    //关闭自动提交
    )
  }

  //producer配置
  private def getProducerConfig: Map[String, Object] = {
    Map(
      ProducerConfig.BOOTSTRAP_SERVERS_CONFIG -> "127.0.0.1:9092",//getString("OUT_BROKER_LIST"),
      ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG -> classOf[IntegerSerializer],   //序列化key
      ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG -> classOf[StringSerializer],  //序列化value
      ProducerConfig.COMPRESSION_TYPE_CONFIG -> "lz4",
      ProducerConfig.ACKS_CONFIG -> "0"
    )
  }

  //获取producer
  private lazy val producer = new KafkaProducer[Int, String](getProducerConfig)

  def getProducer: KafkaProducer[Int, String] = {
    producer
  }

  /**
    * 创建一个dstream
    *
    * @param topic topic名称
    * @param ssc streaming context
    * @return dstream
    */
  def createDirectStream(ssc: StreamingContext, userGroup: String, topic: String): DStream[ConsumerRecord[Int, String]] = {
    val subscribe = ZKSupport.getTopicOffsets(userGroup, topic) match {
      case Some(topicOffsets) =>
        Subscribe[Int, String](
          Seq(topic),
          getConsumerConifg(userGroup),
          topicOffsets
        )
      case None =>
        Subscribe[Int, String](
          Seq(topic),
          getConsumerConifg(userGroup)
        )
    }
    KafkaUtils.createDirectStream[Int, String](
      ssc,
      PreferConsistent,
      subscribe
    )
  }
}
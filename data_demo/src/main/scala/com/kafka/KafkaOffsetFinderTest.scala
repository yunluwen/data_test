package com.kafka

import java.util.Properties

object KafkaOffsetFinderTest {

  def main(args: Array[String]): Unit = {

    val topic = "test"
    val brokers = "localhost:9092"
    //设置属性,配置
    val props = new Properties()
    props.setProperty("bootstrap.servers",brokers)
    //请求的时候需要验证
    props.setProperty("metadata.broker.list",brokers)
    props.setProperty("key.serializer","org.apache.kafka.common.serialization.StringSerializer")
    props.setProperty("value.serializer","org.apache.kafka.common.serialization.StringSerializer")

    // determine offsets
    // 有问题，设置时间没有作用
    val offsetFinder = new KafkaOffsetFinder[String]
    val offsets = offsetFinder.getOffsets(
      topic,
      props,
      s => {
        1548405665979L
      }//,
      // this is the timestamp to look for
      // 1548404631201L
    )
    println(offsets)

  }

}

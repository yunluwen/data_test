package com.zyh.streaming.timewindow.test

import java.util.Properties

import org.apache.flink.api.common.serialization.SimpleStringSchema
import org.apache.flink.api.scala._
import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer010
import org.apache.log4j.{Level, Logger}

object TimeWindowDemo_scala {

  def main(args: Array[String]): Unit = {
    //1.关闭日志，可以减少不必要的日志输出
    Logger.getLogger("org").setLevel(Level.OFF)
    //指定kafka数据流的相关信息
    val zkCluster = "localhost:2181"
    val kafkaCluster = "localhost:9092"
    val kafkaTopicName = "test"

    //1、创建流式处理环境
    val senv = StreamExecutionEnvironment.getExecutionEnvironment
    senv.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)

    //创建kafka数据流
    val properties = new Properties()
    properties.setProperty("zookeeper.connect",zkCluster)
    properties.setProperty("bootstrap.servers",kafkaCluster)
    properties.setProperty("group.id",kafkaTopicName)

    val kafka010 = new FlinkKafkaConsumer010[String](
      kafkaTopicName,new SimpleStringSchema(),properties)

    val text = senv.addSource(kafka010)
      .setParallelism(3)            //设置并行度为3，注意不要超过slot的数量，一个taskManger默认3个slot


  }

}

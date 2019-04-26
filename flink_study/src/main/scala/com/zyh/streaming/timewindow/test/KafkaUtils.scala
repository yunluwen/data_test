package com.zyh.streaming.timewindow.test

import java.lang.System._
import java.text.SimpleDateFormat
import java.util.Date

import com.google.gson.Gson

/**
  * scala和java的重载逻辑不匹配
  * 由于fastjson中有很多重载方法，比如JSON.toString(参数)
  * 使用scala的话需要显式调用带有可变参数的方法
  * 注意点:使用scala的时候，尽量不要使用带有可变参数的重载方法
  */
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}

import scala.util.Random

/**
  * 一秒往kafka发送一条数据
  */
case class Metric(timestamp:Long, name:String,runtime:String)

class KafkaUtils{
  val broker_list = "localhost:9092"
  val topic = "test"

  def writeToKafka(): Unit ={
    val properties = new java.util.Properties()
    properties.setProperty("bootstrap.servers",broker_list)
    //key序列化
    properties.setProperty("key.serializer",
      "org.apache.kafka.common.serialization.StringSerializer")
    //value序列化
    properties.setProperty("value.serializer",
      "org.apache.kafka.common.serialization.StringSerializer")
    val kafkaProducer = new KafkaProducer[String,String](properties)
    val list : List[String] = List("aaaa","bbbb","cccc","dddd")
    //日期类型转换
    val timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")
    val gson = new Gson
    for(i <- 1 to 20){
      val current = currentTimeMillis()
      val metric = Metric(current,
        list(n = Random.nextInt(3)),
        timeFormat.format(new Date(current))
      )
      val record = new ProducerRecord[String, String](topic,
        null, null,gson.toJson(metric,classOf[Metric]))

      kafkaProducer.send(record)
      System.out.println("发送数据: " + gson.toJson(metric,classOf[Metric]))
      Thread.sleep(1000)     //休眠1s
    }
  }
}

object KafkaUtils {
  val kafkaUtils = new KafkaUtils
  def main(args: Array[String]): Unit = {
    kafkaUtils.writeToKafka()
  }
}

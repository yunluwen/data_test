package com.spark.study.streaming.old

import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.SparkConf
import org.apache.spark.streaming.kafka010.ConsumerStrategies.Subscribe
import org.apache.spark.streaming.kafka010.KafkaUtils
import org.apache.spark.streaming.kafka010.LocationStrategies.PreferConsistent
import org.apache.spark.streaming.{Minutes, Seconds, StreamingContext}

import scala.collection.Map

/**
  * sparkStreaming kafka
  * 测试批处理间隔，窗口间隔，滑动间隔的设置
  */
object KafkaWordCount {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local")
      .setAppName("KafkaWordCount")

    val ssc = new StreamingContext(conf,Seconds(10))   //设置的批处理间隔！！！！
    ssc.checkpoint("checkpoint")

    val brokers = "127.0.0.1:9092"
    val kafkaParams = Map[String,Object](
      "bootstrap.servers" -> brokers,
      "key.deserializer" -> classOf[StringDeserializer],
      "value.deserializer" -> classOf[StringDeserializer],
      "group.id" -> "KafkaWordCount",    //自己设置，随意
      "auto.offset.reset" -> "earliest", //"latest",
      "enable.auto.commit" -> (true: java.lang.Boolean) //是否自动提交，默认为false
    )

    val topic = Array("test12")   //主题

    //从kafka中生成dStream
    val dStream = KafkaUtils.createDirectStream(
      ssc,
      PreferConsistent,
      Subscribe[String,String](topic,kafkaParams)
    )

    val datacount = dStream.map(map => {
      (map.value(),1)
      //注意：窗口间隔和滑动间隔的大小一定要设置为批处理间隔的整数倍！！！！
      //滑动窗口默认情况下和批次江歌的相同，而窗口间隔一般设置的要比他们两个都大！！！！！
    }).reduceByKeyAndWindow(_+_,_-_,Minutes(10),Seconds(20),2)

    datacount.print()

    ssc.start()
    ssc.awaitTermination()
  }

}

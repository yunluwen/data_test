package com.adclick.main

import com.adclick.tools.{CalculateAdClickCountByWindow, CalculateProvinceTop3Ad,
  FilterByBlacklist, GenerateDynamicBlacklist,CalculateRealTimeStat}
import com.session.init.Init
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.streaming.kafka010.ConsumerStrategies.Subscribe
import org.apache.spark.streaming.kafka010.KafkaUtils
import org.apache.spark.streaming.kafka010.LocationStrategies.PreferConsistent

import scala.collection.Map

/**
  * streaming 1.6.0
  * ---> 2.2.0
  */
object AdClickRealTimeStatSpark {

  def main(args: Array[String]): Unit = {
    /**
    val sc = Init.initSparkContext()
//    val ssc = sc._3
//    ssc.checkpoint("hdfs://127.0.0.1:9000/wordcount_checkpoint")  //设置检查点，防止数据丢失

    val brokers = "127.0.0.1:9092"
    val kafkaParams = Map[String,Object](
      "bootstrap.servers" -> brokers,
      "key.deserializer" -> classOf[StringDeserializer],
      "value.deserializer" -> classOf[StringDeserializer],
      "group.id" -> "AdClickRealTimeStatSpark",
      "auto.offset.reset" -> "earliest",//"latest",
      "enable.auto.commit" -> (true: java.lang.Boolean)
    )

    val topic = Array("test")

    //从kafka中生成dStream
    val adRealTimeLogDStream = KafkaUtils.createDirectStream(
      ssc,
      PreferConsistent,
      Subscribe[String,String](topic,kafkaParams)
    )

    val filteredAdRealTimeLogDStream = FilterByBlacklist.filterByBlacklist(sc._1,adRealTimeLogDStream)

//    GenerateDynamicBlacklist.generateDynamicBlacklist(filteredAdRealTimeLogDStream)
//    val adRealTimeStatDStream = CalculateRealTimeStat.calculateRealTimeStat(filteredAdRealTimeLogDStream)

//    CalculateProvinceTop3Ad.calculateProvinceTop3Ad(adRealTimeStatDStream)

    CalculateAdClickCountByWindow.calculateAdClickCountByWindow(adRealTimeLogDStream)

    ssc.start()
    ssc.awaitTermination()
**/
  }

}

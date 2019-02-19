package com.spark.study.streaming.old

import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}

object DStreamWordCount {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local")
      .setAppName("DstreamWordCount")

    val ssc = new StreamingContext(conf,Seconds(1))
    //创建socketInputDstream,该inputDstream的receiver监听本地的9999端口
    val lines = ssc.socketTextStream("localhost",9999)

    val words = lines.flatMap(_.split(" "))  //FlatMappedDStream
    val pairs = words.map(word => (word,1))         //MappedDStream
    val wordCounts = pairs.reduceByKey(_+_)         //ShuffledDStream
    wordCounts.print()                              //ForeachDStream

    ssc.start()
    ssc.awaitTermination()

  }

}

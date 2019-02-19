package com.spark.study.streaming.structed

import org.apache.hadoop.hive.ql.exec.spark.session.SparkSession


/**
  * 基于structed streaming的wordCount
  */
object WordCount {

  def main(args: Array[String]): Unit = {
    /**
    val spark = SparkSession.builder()
      .appName("wordCountByStructedStreaming")
      .master("local")
      .getOrCreate()

    //导入一个隐式转换
    import spark.implicits._

    //创建一个输入流
    val lines = spark.readStream   //DataFrame类型
      .format("socket")
      .option("host","localhost")
      .option("port",9999)
      .load()

    val words = lines.as[String].flatMap(_.split(" "))

    val wordCounts = words.groupBy("value").count()

    val query = wordCounts.writeStream      //打印结果
      .outputMode("complete")
      .format("console")
      .start()

    query.awaitTermination()
      **/
  }

}

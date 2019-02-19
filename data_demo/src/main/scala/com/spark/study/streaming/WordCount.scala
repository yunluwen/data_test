package com.spark.study.streaming

import org.apache.spark.sql.SparkSession

/**
  * structured streaming
  * 2.0.0 版本暂时不支持kafka数据源
  */
object WordCount {

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .appName("WordCount")
      .master("local")
//      .enableHiveSupport()      //添加hive支持
      .config("spark.some.config.option", "config-value")  //必须设置spark sql的元数据仓库的地址
      .getOrCreate()

    import spark.implicits._

    val lines = spark.readStream
      .format("socket")
      .option("host","localhost")
      .option("port","9999")
      .load()

    val words = lines.as[String].flatMap(_.split(" "))
    val wordsCount = words.groupBy("value").count()

    //结果的输出
    val query = wordsCount.writeStream
      .outputMode("complete")
      .format("console")
      .start()

    query.awaitTermination()

  }

}

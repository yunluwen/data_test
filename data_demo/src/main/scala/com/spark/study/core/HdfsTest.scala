package com.spark.study.core

import org.apache.spark.sql.SparkSession

/**
  * hdfs
  *
  * 如果能用reduceByKey那就用reduceByKey，因为它会在map端，先进行本地combine，
  * 可以大大的减少要传输到reduce端的数据量，减少网路传输的开销
  * 只有在reduceByKey 处理不了的时候才会用groupbByKey.map()来替代！！！！
  */
object HdfsTest {

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder
      .master("local")
      .appName("hdfsTest")
      .config("spark.some.config.option", "config-value")
      .getOrCreate()
    /**
      * 2.0处理文件中的数据的时候,注意text和textFile的使用
      * txt文件使用textFile,使用text，默认数据两边存在[]
      */
    val file = spark.read.textFile("test_data/data.txt").rdd
    val wordcount = file
      .flatMap(line => line.toString().split(","))
      .cache()
      .map(word => (word,1))
      //.reduceByKey(_+_)
      .groupByKey()     //注意groupBYkey的返回结果<key，每个key的value集合>
      .map(wordcounts => (wordcounts._1,wordcounts._2.sum))  //groupbykey之后再map，得到和reducebykey一样的效果
//      .combineByKey()
      .foreach( wordcounts => {
        println(wordcounts._1+"-->"+wordcounts._2)
      })

    spark.stop()

  }


}


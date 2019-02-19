package com.spark.study.core

import org.apache.spark.sql.SparkSession

/**
  * demo
  */
object HdfsTest2 {

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .master("local")
      .appName("hdfsTest2")
      .config("spark.some.config.option", "config-value")
      .getOrCreate()

    val file = spark.read.textFile("test_data/data.txt").rdd
    val mapped = file.map(line => line.length).cache()

    for(x <- 1 to 10){
      val start = System.currentTimeMillis()
      for(j <- mapped) {
        j+2
      }
      val end = System.currentTimeMillis()
      println("took\t"+(end-start)+" ms")
    }
    spark.stop()
  }
}

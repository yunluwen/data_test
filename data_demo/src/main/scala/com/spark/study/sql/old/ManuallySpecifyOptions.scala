package com.spark.study.sql.old

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.SQLContext

/**
 * @author Administrator
 */
object ManuallySpecifyOptions {
  
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
        .setAppName("ManuallySpecifyOptions")  
    val sc = new SparkContext(conf)
    val sqlContext = new SQLContext(sc)
  
    val peopleDF = sqlContext.read.format("json").load("hdfs://localhost:9000/people.json")
    peopleDF.select("name").write.format("parquet").save("hdfs://localhost:9000/peopleName_scala")
  }
  
}
package com.spark.study.sql.old

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.SQLContext

/**
 * @author Administrator
 */
object ParquetLoadData {
  
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
        .setAppName("ParquetLoadData")  
    val sc = new SparkContext(conf)
    val sqlContext = new SQLContext(sc)
    
    val usersDF = sqlContext.read.parquet("hdfs://localhost:9000/spark-study/users.parquet")
    usersDF.registerTempTable("users")
    val userNamesDF = sqlContext.sql("select name from users")  
    userNamesDF.rdd.map { row => "Name: " + row(0) }.collect()
        .foreach { userName => println(userName) }   
  }
  
}
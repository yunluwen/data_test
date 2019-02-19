package com.spark.study.sql.old

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.SQLContext

/**
 * @author Administrator
 */
object DataFrameOperation {
  
  def main(args: Array[String]) {
    val conf = new SparkConf()
        .setAppName("DataFrameCreate")  
    val sc = new SparkContext(conf)
    val sqlContext = new SQLContext(sc)
  
    val df = sqlContext.read.json("hdfs://localhost:9000/students.json")
    
    df.show()
    df.printSchema()
    df.select("name").show()
    df.select(df("name"), df("age") + 1).show()
    df.filter(df("age") > 18).show()
    df.groupBy("age").count().show()
  }
  
}
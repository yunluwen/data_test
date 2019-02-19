package com.spark.study.sql

import org.apache.spark.sql.{SQLContext, SparkSession}
import org.apache.spark.sql.hive.HiveContext

object HiveContextDemo {
  /**
    * 在用spark本地测试操作hive数据源的时候，注意把hadoop的配置文件core-site.xml和hdfs-site.xml
    * 还有hive的配置文件hive-site.xml复制到resources目录下
    * @param args
    */
  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder
      .master("local[2]")
      .appName("HiveContextDemo2")
      .enableHiveSupport()              //一定要添加这一行，添加对hive的支持
      .config("spark.some.config.option", "config-value")
      .getOrCreate()

    spark.sql(
      """
        |select * from
        |test.sc
        |limit 10
      """.stripMargin).show()

    spark.stop()

  }

}

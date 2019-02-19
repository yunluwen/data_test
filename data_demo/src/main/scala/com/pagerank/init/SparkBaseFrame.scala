package com.pagerank.init

import org.apache.spark.sql.SparkSession

/**
 * @note Spark框架基类,负责提供SparkSession实例
 * 2.0 需要继承才能得到sparkSession的实例对象
 */
trait SparkBaseFrame {

  protected lazy val spark: SparkSession = SparkBaseFrame._spark
}

/**
 * @note 负责生成SparkSession单例
 *
 */
object SparkBaseFrame {

  private lazy val _spark: SparkSession = create()

  /**
   * 必须要配置的项
   * @param builder spark构建对象
   * @return 返回修改后的SparkSession.Builder
   */
  private def setConf(builder: SparkSession.Builder): SparkSession.Builder = {
    builder.config("spark.debug.maxToStringFields", "255")
      .config("spark.streaming.kafka.maxRatePerPartition", "1000")
  }

  /**
   * 创建SparkSession实例方法
   * @return SparkSession实例
   */
  private def create(): SparkSession = {
    val builder = SparkSession.builder()
    val spark = setConf(builder).getOrCreate()

    spark.sparkContext.setLogLevel("WARN")
    spark
  }
}
package com.session.init

import com.session.constants.Constants
import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.SQLContext
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.{SparkConf, SparkContext}

/**
  * spark配置初始化
  * spark 1.6.0 版本
  */
object Init {
  /**
    * 初始化spark、sql环境
    *
    * @return
    */
  def initSparkContext(): (SparkContext, SQLContext,StreamingContext) = {
    // spark配置文件
    val conf = getSparkConf()
    // spark上下文环境
    val sc = new SparkContext(conf)
    // SQL上下文环境
    val sqlContext = getSQLContext(sc)
    // Streaming上下文环境
//    当spark streaming和spark sql一起使用时我们要注意只能有一个SparkContext,否则就会报如下错误:
//    org.apache.spark.SparkException: Only one SparkContext may be running in this JVM
//    这里，日过通过conf创建streamingContext就会出现问题
//    val streamingContext = getStreamingContext(conf)
    val streamingContext = getStreamContext(sc)
    // 设置Log等级
    Logger.getRootLogger.setLevel(Level.OFF)

    (sc, sqlContext,streamingContext)
  }

  /**
    * 加载spark配置，如果在本地使用2核，如果在集群，则提交作业时候指定
    *
    * @return
    */
  def getSparkConf(): SparkConf = {

    new SparkConf().setAppName(Constants.SPARK_APP_NAME).setMaster(Constants.SPARK_MASTER)

  }

  /**
    * 加载SQL上下环境，如果在本地生成sql环境
    *
    * @param sc
    * @return
    */
  def getSQLContext(sc: SparkContext): SQLContext = {

    new SQLContext(sc)

  }

  /**
    * 通过conf创建streamingContext
    * @param conf
    * @return
    */
  def getStreamingContext(conf:SparkConf): StreamingContext = {

    new StreamingContext(conf, Seconds(5))

  }

  /**
    * 通过sparkContext创建streamingContext
    * @param sc
    * @return
    */
  def getStreamContext(sc: SparkContext) : StreamingContext = {

    new StreamingContext(sc, Seconds(5))

  }


}
package com.zyh.streaming.timewindow.test

import java.text.SimpleDateFormat
import java.util.Date

import com.google.gson.Gson
import org.apache.flink.api.common.serialization.SimpleStringSchema
import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.functions.AssignerWithPeriodicWatermarks
import org.apache.flink.streaming.api.scala.{DataStream, StreamExecutionEnvironment, _}
import org.apache.flink.streaming.api.watermark.Watermark
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer010
import org.apache.log4j.{Level, Logger}
/**
  * eventTime window触发测试
  * 需要理解的深入一点
  * 通过watermark+window机制来处理乱序数据问题
  */
object EventTimeWindowTest_scala {

  /**
    * case class
    * 默认实现了序列化:java.io.Serializable
    * @param timestamp
    * @param name
    * @param runtime
    */
  case class Metric(timestamp:Long, name:String,runtime:String)

  def main(args: Array[String]): Unit = {
    //1.关闭日志，可以减少不必要的日志输出
    Logger.getLogger("org").setLevel(Level.OFF)
    val zkCluster = "localhost:2181"   //消费者需要，生产者不需要
    val kafkaCluster = "localhost:9092"
    val topicName = "test"

    val senv = StreamExecutionEnvironment.getExecutionEnvironment
    //设置时间类型
    senv.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)

    val properties = new java.util.Properties()
    properties.setProperty("zookeeper.connect",zkCluster)
    properties.setProperty("bootstrap.servers",kafkaCluster)
    properties.setProperty("group.id",topicName)

    val kafka010 = new FlinkKafkaConsumer010[String](
      topicName,
      new SimpleStringSchema(),     //字符串序列化
      properties                    //配置信息
    )

    val data = senv.addSource(kafka010)  //添加数据源
      .setParallelism(1)            //设置并行度  //调整并行度测试

    //对数据进行转换
    val map = data.map(f => {
      val gson = new Gson() //写到算子内部，防止出现未序列化的异常
      val arr = gson.fromJson(f,classOf[Metric])
      arr
    })

    //设置水位线
    val watermark:DataStream[Metric] =
      map.assignTimestampsAndWatermarks(new AssignerWithPeriodicWatermarks[Metric] {
        val maxOutOfOrderness = 3000L    //允许的最大乱序时间 ： 3s
        var currentMaxTimestamp = 0L    //当前最大时间戳
        //获取当前水位线
        override def getCurrentWatermark: Watermark =
          return new Watermark(currentMaxTimestamp - maxOutOfOrderness)

        override def extractTimestamp(t: Metric, l: Long): Long = {
          val timestamp = t.timestamp
          val format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")
          println(
            s"${t.name}\t${t.timestamp}\t${t.runtime}\t${
              format.format(new Date(getCurrentWatermark.getTimestamp))}\t")
          currentMaxTimestamp = Math.max(timestamp,l)
          timestamp
        }
    })

    //窗口操作
    val window = watermark.keyBy("name")
      .timeWindow(Time.seconds(3))
        .sum("timestamp")

    window.print()

    senv.execute(this.getClass.getName)
  }

}

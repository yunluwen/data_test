package com.zyh.streaming

import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.streaming.api.scala.{DataStream, StreamExecutionEnvironment}
import org.apache.flink.api.scala._
import org.apache.log4j.{Level, Logger}

/**
  * 全局流计算
  * tumbling-time-window (无重叠数据)
  * 每5秒钟统计一次，在这过去的5秒钟内，各个路口通过红绿灯汽车的数量。
  */
object TumblingTW {

  def main(args: Array[String]): Unit = {
    //1.关闭日志，可以减少不必要的日志输出
    Logger.getLogger("org").setLevel(Level.OFF)

    //1、创建流式处理环境
    val senv = StreamExecutionEnvironment.getExecutionEnvironment

    //2、准备数据来源
    val text = senv.socketTextStream("localhost",9999)

    //3.转换数据格式，text->CarWc
    case class CarWc(sensorId: Int, carCnt: Int)
    val text1:DataStream[CarWc] = text.map(
      (f) => {
        val tokens = f.split(",")
        CarWc(tokens(0).trim.toInt,tokens(1).trim.toInt)
      }
    )

    //4.执行统计操作，每个sensorId一个tumbling窗口，窗口的大小为5秒
    //也就是说，每5秒钟统计一次，在这过去的5秒钟内，各个路口通过红绿灯汽车的数量。
    val text3 = text1
      .keyBy("sensorId")
      .timeWindow(Time.seconds(5))
      .sum("carCnt")

    //5、显示统计结果
    text3.print()

    //触发流计算
    senv.execute(this.getClass.getName)

  }

}

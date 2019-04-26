package com.zyh.streaming

import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows
import org.apache.flink.streaming.api.windowing.time.Time

/**
  * window总结
  * 1.flink支持两种划分窗口的方式（time和count）
  * 如果根据时间划分窗口，那么它就是一个time-window
  * 如果根据数据划分窗口，那么它就是一个count-window
  * 2.flink支持窗口的两个重要属性（size和interval）
  * 如果size=interval,那么就会形成tumbling-window(无重叠数据)
  * 如果size>interval,那么就会形成sliding-window(有重叠数据)
  * 如果size<interval,那么这种窗口将会丢失数据。比如每5秒钟，统计过去3秒的通过路口汽车的数据，将会漏掉2秒钟的数据。
  * 3.通过组合可以得出四种基本窗口
  * time-tumbling-window 无重叠数据的时间窗口，设置方式举例：timeWindow(Time.seconds(5))
  * time-sliding-window  有重叠数据的时间窗口，设置方式举例：timeWindow(Time.seconds(5), Time.seconds(3))
  * count-tumbling-window无重叠数据的数量窗口，设置方式举例：countWindow(5)
  * count-sliding-window 有重叠数据的数量窗口，设置方式举例：countWindow(5,3)
  * 4.flink支持在stream上的通过key去区分多个窗口
  *
  * 1.现实世界中的时间是不一致的，在flink中被划分为事件时间，提取时间，处理时间三种。
  * 2.如果以EventTime为基准来定义时间窗口那将形成EventTimeWindow,要求消息本身就应该携带EventTime
  * 2.如果以IngesingtTime为基准来定义时间窗口那将形成IngestingTimeWindow,以source的systemTime为准。
  * 2.如果以ProcessingTime基准来定义时间窗口那将形成ProcessingTimeWindow，以operator的systemTime为准。
  *
  * 使用EventTime用法示例
  * 1.要求消息本身就应该携带EventTime
  *
  * 2.时间对应关系如下
  * 2016-04-27 11:34:22  1461756862000
  * 2016-04-27 11:34:27  1461756867000
  * 2016-04-27 11:34:32  1461756872000
  *
  * 输入数据:
  * 1461756862000,boos1,pc1,100.0
  * 1461756867000,boos2,pc1,200.0
  * 1461756872000,boos1,pc1,300.0
  * 1461756862000,boos2,pc2,500.0
  * 1461756867000,boos2,pc2,600.0
  * 1461756872000,boos2,pc2,700.0
  *
  * 以EventTime划分窗口，计算5秒钟内出价最高的信息
  */
object EventTimeExample {
  def main(args: Array[String]) {

    //1.创建执行环境，并设置为使用EventTime
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    //置为使用EventTime
    env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)

    //2.创建数据流，并进行数据转化
    val source = env.socketTextStream("localhost", 9999)
    case class SalePrice(time: Long, boosName: String, productName: String, price: Double)
    val dst1: DataStream[SalePrice] = source.map(value => {
      val columns = value.split(",")
      SalePrice(columns(0).toLong, columns(1), columns(2), columns(3).toDouble)
    })

    //3.使用EventTime进行求最值操作
    val dst2: DataStream[SalePrice] = dst1
      //提取消息中的时间戳属性
      .assignAscendingTimestamps(_.time)
      .keyBy(_.productName)
      //.timeWindow(Time.seconds(5))//设置window方法一
      .window(TumblingEventTimeWindows.of(Time.seconds(5)))//设置window方法二
      .max("price")

    //4.显示结果
    dst2.print()

    //5.触发流计算
    env.execute()
  }
}
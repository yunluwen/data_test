package com.zyh.streaming

import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment

/**
  * 生成时间戳，水印
  *
  * 有两种方法可以分配时间戳并生成水印：
  * 直接在数据流源中
  * 通过时间戳分配器/水印生成器：在Flink中，时间戳分配器还定义要发出的水印
  *
  * EnentTime
  */
object EventTimeTest_scala {

  def main(args: Array[String]): Unit = {
    //1、创建流式处理环境
    val senv = StreamExecutionEnvironment.getExecutionEnvironment
    //2、设置时间特性
    senv.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)





  }

}

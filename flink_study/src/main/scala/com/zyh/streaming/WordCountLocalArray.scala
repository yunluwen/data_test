package com.zyh.streaming

import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.api.scala._
/**
  * 流式wordcount
  */
object WordCountLocalArray {

  def main(args: Array[String]): Unit = {
    //1、创建流式处理环境
    val senv = StreamExecutionEnvironment.getExecutionEnvironment

    //2.准备数据
    val text = senv.fromElements(
      "To be, or not to be,that is the question",
      "Whether 'tis nobler in the mind to suffer",
      "The slings and arrows of outrageous fortune",
      "Or to take arms against a sea of troubles,")

    //3、执行计算
    val counts =
      text.flatMap(_.toUpperCase.split(" ")).map((_,1)).keyBy(0).sum(1)
    //4、将计算结果打印出来
    counts.print()
    //5、触发流计算
    senv.execute("flink streaming wordcount ...")
  }
}

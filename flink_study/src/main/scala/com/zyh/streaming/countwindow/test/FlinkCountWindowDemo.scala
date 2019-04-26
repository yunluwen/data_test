package com.zyh.streaming.countwindow.test

import org.apache.flink.api.common.functions.ReduceFunction
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.streaming.api.scala._

/**
  * countWindow的简单使用
  */
object FlinkCountWindowDemo {

  def main(args: Array[String]): Unit = {
    val senv = StreamExecutionEnvironment.getExecutionEnvironment
    senv.setParallelism(1)   //设置并行度

    val data = senv.addSource(new StreamDataSource)

    val output = data
      .keyBy(0)
      .countWindow(3)   //3条数据计算一次
      .reduce(new ReduceFunction[(String, String)] {
      override def reduce(t: (String, String),
                          t1: (String, String)): (String, String) = {
        (t._1,t1._1+" "+t1._2)
      }
    })

    output.print()
    senv.execute(this.getClass.getName)
  }
}

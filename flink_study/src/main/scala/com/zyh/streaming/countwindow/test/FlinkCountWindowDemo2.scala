package com.zyh.streaming.countwindow.test

import org.apache.flink.api.common.functions.ReduceFunction
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.streaming.api.scala._

/**
  * 有一个滑动计数窗口，每2个元素计算一次最近3个元素的总和
  * 其实就是设置窗口大小为3，移动步数为2
  */
object FlinkCountWindowDemo2 {

  def main(args: Array[String]): Unit = {
    val senv = StreamExecutionEnvironment.getExecutionEnvironment
    senv.setParallelism(1)   //设置并行度

    val data = senv.addSource(new StreamDataSource)

    val output = data
      .keyBy(0)     //注意，是按key进行分组
      .countWindow(3,2)
      .reduce(new ReduceFunction[(String, String)] {
        override def reduce(t: (String, String),
                            t1: (String, String)):
        (String, String) = {
          (t._1,t._2+" "+t1._2)
        }
      })

    output.print()
    senv.execute(this.getClass.getName)
  }
}

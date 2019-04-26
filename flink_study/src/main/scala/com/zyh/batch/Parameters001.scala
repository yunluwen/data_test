package com.zyh.batch

import org.apache.flink.api.common.functions.RichMapFunction
import org.apache.flink.api.scala.ExecutionEnvironment
import org.apache.flink.api.scala._

/**
  * flink中支持向Function传递参数，常见的有两种方式，
  * 1.通过构造方法向Function传递参数
  * 2.通过ExecutionConfig向Function传递参数
  */
object Parameters001 {

  def main(args: Array[String]): Unit = {
    /**
      * 通过构造函数向function传递参数
      */
    val env = ExecutionEnvironment.getExecutionEnvironment

    val data = env.fromElements(22.0,23.0,24.0,25.0)

    val bu = 2.0
    data.map(new MyMapper(bu)).print()
    class MyMapper(bu:Double) extends RichMapFunction[Double,Double]{
      override def map(in: Double): Double = {
        in + bu
      }
    }
  }

}

package com.zyh.batch

import org.apache.flink.api.common.functions.RichMapFunction
import org.apache.flink.api.scala.{DataSet, ExecutionEnvironment}
import org.apache.flink.api.scala._   //scala的flink程序必须引入这个
/**
  * 通过构造方法向Function传递参数(复合数据)
  */
object Parameters002 {

  def main(args: Array[String]): Unit = {
    val env = ExecutionEnvironment.getExecutionEnvironment

    //1.准备工人数据
    case class Worker(name: String, salaryPerMonth: Double)
    val workers: DataSet[Worker] = env.fromElements(
      Worker("zhagnsan", 1356.67),
      Worker("lisi", 1476.67)
    )
    //2.准备工作月份数据，作为参数传递出去
    val month = 4

    workers.map(new MyMapper(month)).print()
    class MyMapper(month:Int) extends RichMapFunction[Worker,Worker]{
      override def map(in: Worker): Worker = {
        val name = in.name
        val salary = in.salaryPerMonth * month
        Worker(name,salary)
      }
    }
  }


}

package com.zyh.batch

import org.apache.flink.api.scala.{ExecutionEnvironment, _}
import org.apache.flink.api.common.functions.FilterFunction
import org.apache.flink.api.scala._
/**
  * filter:
  * 以element为粒度，对element进行过滤操作。将满足过滤条件的element组成新的DataSet
  */
object FilterFunction001scala {

  def main(args: Array[String]): Unit = {
    // 1.设置运行环境,并创造测试数据
    val env = ExecutionEnvironment.getExecutionEnvironment
    val text = env.fromElements(2, 4, 7, 8, 9, 6)

    //2.对DataSet的元素进行过滤，筛选出偶数元素
    val filter = text.filter(new FilterFunction[Int] {
      override def filter(t: Int): Boolean = {
        t%2 == 0
      }
    })
    filter.print()

    println("-------------------")
    //3.对DataSet的元素进行过滤，筛选出大于5的元素
    val filter2 = text.filter(new FilterFunction[Int] {
      override def filter(t: Int): Boolean = {
        t > 5
      }
    })
    filter2.print()
  }
}

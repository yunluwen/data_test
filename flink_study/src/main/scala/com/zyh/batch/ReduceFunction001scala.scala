package com.zyh.batch

import org.apache.flink.api.common.functions.ReduceFunction   //使用的function,引入这个
import org.apache.flink.api.scala.{ExecutionEnvironment, _}
import org.apache.flink.api.scala._
/**
  * reduce算子：
  * 以element为粒度，对element进行合并操作。最后只能形成一个结果。
  */
object ReduceFunction001scala {

  def main(args: Array[String]): Unit = {
    // 1.设置运行环境,并创造测试数据
    val env = ExecutionEnvironment.getExecutionEnvironment
    val text = env.fromElements(1, 2, 3, 4, 5, 6, 7)

    //2.对DataSet的元素进行合并，这里是计算累加和
    val sum = text.reduce(new ReduceFunction[Int] {
      override def reduce(t: Int, t1: Int): Int = {
        t+t1
      }
    })
    sum.print()

    //3.对DataSet的元素进行合并，这里是计算累乘积
    val cheng = text.reduce(new ReduceFunction[Int] {
      override def reduce(t: Int, t1: Int): Int = {
        t*t1
      }
    })
    cheng.print()

    //4.对DataSet的元素进行合并，逻辑可以写的很复杂
    val text2 = text.reduce(new ReduceFunction[Int] {
      override def reduce(t: Int, t1: Int): Int = {
        if (t % 2 == 0) {
          t + t1
        } else {
          t * t1
        }
      }
    })
    text2.print()

    //5.对DataSet的元素进行合并，可以看出intermediateResult是临时合并结果，next是下一个元素
    val text5 = text.reduce(new ReduceFunction[Int] {
      override def reduce(intermediateResult: Int, next: Int): Int = {
        println("intermediateResult=" + intermediateResult + " ,next=" + next)
        intermediateResult + next
      }
    })
    text5.collect()
  }
}

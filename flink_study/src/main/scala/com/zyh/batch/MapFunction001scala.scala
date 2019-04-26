package com.zyh.batch

import org.apache.flink.api.common.functions.MapFunction   //使用的function,引入这个
import org.apache.flink.api.scala.{ExecutionEnvironment, _}
import org.apache.flink.api.scala._

/**
  * map算子操作:
  * 以element为粒度，对element进行1：1的转化
  */
object MapFunction001scala {

  def main(args: Array[String]): Unit = {
    // 1.设置运行环境,并创造测试数据
    val env = ExecutionEnvironment.getExecutionEnvironment
    val text = env.fromElements("flink vs spark", "buffer vs  shuffer")

    // 2.以element为粒度，将element进行map操作，转化为大写并添加后缀字符串"--##bigdata##"
    val map1 = text.map(new MapFunction[String, String] {
      override def map(s: String): String = s.toUpperCase() + "--##bigdata##"
    })
    map1.print()

    // 3.以element为粒度，将element进行map操作，转化为大写并,并计算line的长度。
    val map2 = text.map(new MapFunction[String,(String,Int)] {
      override def map(t: String): (String, Int) = (t.toUpperCase(),t.length)
    })
    map2.print()

    // 4.以element为粒度，将element进行map操作，转化为大写并,并计算line的长度。
    //4.1定义class
    case class Wc(line: String, lenght: Int)
    //4.2转化成class类型
    val text4 = text.map(new MapFunction[String, Wc] {
      override def map(s: String): Wc = Wc(s.toUpperCase(), s.length)
    })
    text4.print()
  }
}

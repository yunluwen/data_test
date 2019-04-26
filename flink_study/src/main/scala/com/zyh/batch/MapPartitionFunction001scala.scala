package com.zyh.batch

import java.lang

import org.apache.flink.api.scala.{ExecutionEnvironment, _}
import org.apache.flink.api.common.functions.MapPartitionFunction
import org.apache.flink.util.Collector
import org.apache.flink.api.scala._

/**
  * mapPartition算子：
  * 以partition为粒度，对element进行1：1的转化。有时候会比map效率高。
  *
  * 未知原因:程序运行的很慢，待解决
  */
object MapPartitionFunction001scala {

  def main(args: Array[String]): Unit = {
    // 1.设置运行环境,创造测试数据
    val env = ExecutionEnvironment.getExecutionEnvironment
    val text = env.fromElements("flink vs spark", "buffer vs  shuffer")

    //2.以partition为粒度，进行map操作，计算element个数
    val map1 = text.mapPartition(new MapPartitionFunction[String,Int] {
      override def mapPartition(iterable: lang.Iterable[String], collector: Collector[Int]): Unit = {
        var c = 0
        val iterator = iterable.iterator()
        while(iterator.hasNext){
          c = c + 1
        }
        collector.collect(c)
      }
    })
    map1.print()

    //3.以partition为粒度，进行map操作，转化element内容
    val map2 = text.mapPartition(new MapPartitionFunction[String,String] {
      override def mapPartition(iterable: lang.Iterable[String], collector: Collector[String]): Unit = {
        val iterator = iterable.iterator()
        while(iterator.hasNext){
          val line = iterator.next().toUpperCase
          collector.collect(line)
        }
      }
    })
    map2.print()

    //4.以partition为粒度，进行map操作，转化为大写并,并计算line的长度。
    //4.1定义class
    case class Wc(line: String, lenght: Int)
    //4.2转化成class类型,自动转化为case class类型
    val text4 = text.mapPartition(new MapPartitionFunction[String, Wc] {
      override def mapPartition(iterable: lang.Iterable[String], collector: Collector[Wc]): Unit = {
        val itor = iterable.iterator
        while (itor.hasNext) {
          var s = itor.next()
          collector.collect(Wc(s.toUpperCase(), s.length))
        }
      }
    })
    text4.print()
  }
}

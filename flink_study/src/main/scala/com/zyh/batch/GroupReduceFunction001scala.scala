package com.zyh.batch

import java.lang

import org.apache.flink.api.common.functions.GroupReduceFunction
import org.apache.flink.api.scala.{ExecutionEnvironment, _}
import org.apache.flink.util.Collector
import org.apache.flink.api.scala._
/**
  * reduceGroup:
  * 对每一组的元素分别进行合并操作。与reduce类似，不过它能为每一组产生一个结果。
  * 如果没有分组，就当作一个分组，此时和reduce一样，只会产生一个结果。
  */
object GroupReduceFunction001scala {

  def main(args: Array[String]): Unit = {
    // 1.设置运行环境,并创造测试数据
    val env = ExecutionEnvironment.getExecutionEnvironment
    val text = env.fromElements(1, 2, 3, 4, 5, 6, 7)

    //2.对DataSet的元素进行分组合并，这里是计算累加和
    val text1 = text.reduceGroup(new GroupReduceFunction[Int,Int] {
      override def reduce(iterable: lang.Iterable[Int], collector: Collector[Int]): Unit = {
        var sum = 0
        val iterator = iterable.iterator()
        while(iterator.hasNext){
          sum = sum + iterator.next()
        }
        collector.collect(sum)
      }
    })
    text1.print()

    //3.对DataSet的元素进行分组合并，这里是分别计算偶数和奇数的累加和
    val text2 = text.reduceGroup(new GroupReduceFunction[Int,(Int,Int)] {
      override def reduce(iterable: lang.Iterable[Int], collector: Collector[(Int,Int)]): Unit = {
        var sum0 = 0
        var sum1 = 0
        val iterator = iterable.iterator()
        while(iterator.hasNext){
          val next = iterator.next()
          if(next%2==0){
            sum0 = sum0 + next
          }else{
            sum1 = sum1 + next
          }
        }
        collector.collect((sum0,sum1))
      }
    })
    text2.print()

    //4.对DataSet的元素进行分组合并，这里是对分组后的数据进行合并操作，统计每个人的工资总和（每个分组会合并出一个结果）
    val data = env.fromElements(
      ("zhangsan", 1000), ("lisi", 1001), ("zhangsan", 3000), ("lisi", 1002))
    //4.1根据name进行分组，
    val data1 = data.groupBy(0).reduceGroup(
      new GroupReduceFunction[(String,Int),(String,Int)] {
        override def reduce(iterable: lang.Iterable[(String, Int)],
                            collector: Collector[(String, Int)]): Unit = {
          var salary = 0     //薪水
          var name = ""
          val iterator = iterable.iterator()
          while(iterator.hasNext){
            var node = iterator.next()
            name = node._1
            salary = salary + node._2
          }
          collector.collect((name,salary))
        }
    })
    data1.print()

  }
}

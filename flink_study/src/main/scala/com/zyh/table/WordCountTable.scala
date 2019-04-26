package com.zyh.table

import org.apache.flink.api.scala.ExecutionEnvironment
import org.apache.flink.table.api.TableEnvironment
import org.apache.flink.api.scala._
import org.apache.flink.table.api.scala._  //需要导入这个，才能使用Table API的方法
/***
  * Table API
  * 简单测试 wordCount
  */
object WordCountTable {

  case class WC(word: String, frequency: Long)

  def main(args: Array[String]): Unit = {
    //构建Table运行环境
    val env = ExecutionEnvironment.getExecutionEnvironment
    val tEnv = TableEnvironment.getTableEnvironment(env)

    val input = env.fromElements(WC("hello", 1), WC("hello", 1), WC("ciao", 1))
    val expr = input.toTable(tEnv)

    val result = expr
      .groupBy('word)    //分组字段
      .select('word, 'frequency.sum as 'frequency)  //查询数据
      .filter('frequency === 2)  //过滤数据
      .toDataSet[WC]                //转换为DataSet

    result.print()
  }

}

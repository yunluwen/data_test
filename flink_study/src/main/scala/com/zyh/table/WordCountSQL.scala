package com.zyh.table

import org.apache.flink.api.scala.ExecutionEnvironment
import org.apache.flink.table.api.TableEnvironment
import org.apache.flink.api.scala._
import org.apache.flink.table.api.scala._  //需要导入这个，才能使用Table API的方法

/**
  * sql API测试
  */
object WordCountSQL {

  case class WC(word: String, frequency: Long)

  def main(args: Array[String]): Unit = {
    // set up execution environment
    val env = ExecutionEnvironment.getExecutionEnvironment
    val tEnv = TableEnvironment.getTableEnvironment(env)

    val input = env.fromElements(WC("hello", 1), WC("hello", 1), WC("ciao", 1))

    // register the DataSet as table "WordCount"
    // 将数据注册为一张表: 表名，数据，字段名
    tEnv.registerDataSet("WordCount", input, 'word, 'frequency)

    // run a SQL query on the Table and retrieve the result as a new Table
    // 执行一条sql
    val table = tEnv.sqlQuery(
      "SELECT word, SUM(frequency) FROM WordCount GROUP BY word")

    //将数据转换为DataSet并输出结果
    table.toDataSet[WC].print()
  }
}

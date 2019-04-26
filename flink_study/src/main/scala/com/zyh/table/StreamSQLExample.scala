package com.zyh.table

import org.apache.flink.table.api.TableEnvironment
import org.apache.flink.api.scala._
import org.apache.flink.table.api.scala._  //需要导入这个，才能使用Table API的方法
import org.apache.flink.streaming.api.scala.{DataStream, StreamExecutionEnvironment}

/**
  * sql API测试： 流式
  */
object StreamSQLExample {

  //订单
  case class Order(user: Long, product: String, amount: Int)

  def main(args: Array[String]): Unit = {
    // set up execution environment
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    val tEnv = TableEnvironment.getTableEnvironment(env)

    val orderA: DataStream[Order] = env.fromCollection(Seq(
      Order(1L, "beer", 3),
      Order(1L, "diaper", 4),
      Order(3L, "rubber", 2)))

    val orderB: DataStream[Order] = env.fromCollection(Seq(
      Order(2L, "pen", 3),
      Order(2L, "rubber", 3),
      Order(4L, "beer", 1)))

    //两种将dataStream注册成表的方式
    // convert DataStream to Table
    var tableA = tEnv.fromDataStream(orderA, 'user, 'product, 'amount)
    // register DataStream as Table
    tEnv.registerDataStream("OrderB", orderB, 'user, 'product, 'amount)

    val result = tEnv.sqlQuery(s"select * from $tableA where amount>2 union all " +
      s"select * from OrderB where amount<2")
    result.toAppendStream[Order].print()

    env.execute(this.getClass.getName)

  }
}

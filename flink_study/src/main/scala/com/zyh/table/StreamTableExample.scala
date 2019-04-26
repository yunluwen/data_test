package com.zyh.table

import org.apache.flink.table.api.TableEnvironment
import org.apache.flink.api.scala._
import org.apache.flink.table.api.scala._  //需要导入这个，才能使用Table API的方法
import org.apache.flink.streaming.api.scala.{DataStream, StreamExecutionEnvironment}

/**
  * Table API流式数据
  */
object StreamTableExample {
  //订单
  case class Order(user: Long, product: String, amount: Int)

  def main(args: Array[String]): Unit = {
    // set up execution environment
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    val tEnv = TableEnvironment.getTableEnvironment(env)

    val orderA = env.fromCollection(Seq(   //Sequence
      Order(1L, "beer", 3),
      Order(1L, "diaper", 4),
      Order(3L, "rubber", 2))).toTable(tEnv)  //注意这里的这个方法

    val orderB = env.fromCollection(Seq(
      Order(2L, "pen", 3),
      Order(2L, "rubber", 3),
      Order(4L, "beer", 1))).toTable(tEnv)

    val result: DataStream[Order] = orderA.unionAll(orderB)
      .select('user,'product,'amount)
      .where('amount > 2)
      .toAppendStream[Order]

    result.print()

    env.execute(this.getClass.getName)
  }
}
